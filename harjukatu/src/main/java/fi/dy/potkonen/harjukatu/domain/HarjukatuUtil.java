/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain;

import static fi.dy.potkonen.harjukatu.domain.Harjukatu.CLAMD;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.FILEPATH;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.ERROR;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.OK;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MINUTE;
import fi.solita.clamav.ClamAVClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

/**
 *
 * @author esa
 */
public class HarjukatuUtil {
    private static Logger logger = LoggerFactory.getLogger("Harjukatu");

    public static List<MenuItem> mapMenuItems(ResultSet rs) throws SQLException {
        List<MenuItem> list = new ArrayList<MenuItem>();
        while (rs.next()) {
            MenuItem mi = new MenuItem();
            mi.setId(rs.getInt("ID"));
            mi.setTitle(rs.getString("TITLE"));
            mi.setAction(rs.getString("ACTION"));
            mi.setDescription(rs.getString("DESCRIPTION"));
            mi.setTarget(rs.getString("TARGET"));
            list.add(mi);
        }
        return list;
    }

    public static List<Post> mapPostItems(ResultSet rs) throws SQLException {
        List<Post> list = new ArrayList<Post>();
        while (rs.next()) {
            Post pt = new Post();
            pt.setId(rs.getInt("ID"));
            pt.setTitle(rs.getString("TITLE"));
            pt.setUrl(rs.getString("URL"));
            pt.setDescription(rs.getString("DESCRIPTION"));
            pt.setBanner(rs.getString("BANNER"));
            list.add(pt);
        }
        return list;
    }
    
    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isEmpty(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
    
    static void addGPSData(byte[] bytes) {
    }
    
    static void fillTime(Reply reply) {
    }
    
    public static Reply store(String name, byte[] bytes, String ip) throws Exception {
        // Inform sender
        UploadItem item = new UploadItem();
        item.setIp(ip);
        Reply ry = new Reply(OK,"Got store "+name+" request.\n");
        ry.setItem(item);
        fillTime(ry);
        // Virus check
        ClamAVClient cl = new ClamAVClient("localhost", CLAMD, MINUTE);
        byte[] reply = cl.scan(bytes);

        if (!ClamAVClient.isCleanReply(reply)) {
            String msg = "ClamAV found something! REJECT from client["+ip+"]";
            ry.setType(ERROR);
            ry.addMessage(msg);
            logger.warn(msg);
        } else { // In memory scan done. Safe enough to save.
            InputStream in = new ByteArrayInputStream(bytes);
            File fl = new File(FILEPATH,name);
            String msg = "File "+fl.getName()+" accepted!";
            FileUtils.copyInputStreamToFile(in,fl);
            // Fill GPS
            addGPSData(bytes);
            ry.addMessage(msg);
            logger.info(msg);
        }
        return ry;
    }
}
