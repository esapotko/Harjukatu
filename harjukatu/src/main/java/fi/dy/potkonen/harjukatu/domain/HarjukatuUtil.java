/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author esa
 */
public class HarjukatuUtil {

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
    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
    {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }    
}
