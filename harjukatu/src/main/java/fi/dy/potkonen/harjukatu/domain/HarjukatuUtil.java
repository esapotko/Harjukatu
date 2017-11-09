/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain;

import static fi.dy.potkonen.harjukatu.domain.Harjukatu.CLAMD;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.CLAMHOST;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.FILEPATH;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.IMAGEPATTERN;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.ERROR;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.OK;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MINUTE;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.SLIDEURL;
import fi.solita.clamav.ClamAVClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
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
    
    static Reply fillInfo(byte[] bytes, String name) {
        // Inform sender
        UploadItem item = new UploadItem();
        item.setPath(FILEPATH + name);
        item.setCreated(Calendar.getInstance());
        
        Reply reply = new Reply(OK, "Got store " + name + " request.\n");
        reply.setItem(item);
        try {
            // Enable virus check testfile (not a image)
            if (!"EICAR".equals(name)) {
                ImageIO.read(new ByteArrayInputStream(bytes)).toString();
            }
            // It's an image (only BMP, GIF, JPG and PNG are recognized).
        } catch (Exception e) {
            reply.setType(ERROR);
            reply.addMessage("File is not an acceptable image.");
        }
        // Todo Check distance from harjukatu. 0.5 km range Max
        return reply;
    }
    
    public static Reply store(String name, byte[] bytes) throws Exception {
        // Basic check. Type, location
        Reply ry = fillInfo(bytes, name);
        // Virus check
        ClamAVClient cl = new ClamAVClient(CLAMHOST, CLAMD, MINUTE);
        byte[] reply = cl.scan(bytes);
        
        if (!ClamAVClient.isCleanReply(reply)) {
            String msg = "ClamAV found something! REJECT";
            ry.setType(ERROR);
            ry.addMessage(msg);
            logger.warn(msg);
        } else { // In memory scan done. Safe enough to save.
            InputStream in = new ByteArrayInputStream(bytes);
            File fl = new File(FILEPATH, name);
            FileUtils.copyInputStreamToFile(in, fl);
            String msg = "File " + fl.getName() + " accepted!";
            ry.addMessage(msg);
            logger.info(msg);
        }
        return ry;
    }
    
    public static List<Slide> listSlides() {
        List<Slide> slides = new ArrayList<Slide>(); 
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().matches(IMAGEPATTERN);
            }
        };
        File in = new File(FILEPATH);
        File[] paths = in.listFiles(filter);
        
        for(File path:paths) {
            slides.add(new Slide(SLIDEURL+path.getName(),""));
        }
        
        return slides;
    }
}
