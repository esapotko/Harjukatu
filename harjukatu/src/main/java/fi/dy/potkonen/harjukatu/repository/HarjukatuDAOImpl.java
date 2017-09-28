/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.repository;

import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author esa
 */
@Repository
public class HarjukatuDAOImpl implements HarjukatuDAO {
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<MenuItem> getTopMenu(){
        String sql = "select * from MenuItem where Position = 'Top' ";
        List<MenuItem> list = jdbcTemplate.query(sql, new RowMapper<MenuItem>(){
 
            public MenuItem mapRow(ResultSet rs, int rownum) throws SQLException {
                MenuItem mi = new MenuItem();
                mi.setId(rs.getInt("ID"));
                mi.setTitle(rs.getString("TITLE"));
                mi.setAction(rs.getString("ACTION"));
                mi.setDescription(rs.getString("DESCRIPTION"));
                mi.setTarget(rs.getString("TARGET"));
                return mi;
            }
            
        });
        return list;
    }    
    public List<Post> getAllPosts(){
        String sql = "select * from Posts where Priority > '1' order by ID";
        List<Post> list = jdbcTemplate.query(sql, new RowMapper<Post>(){
 
            public Post mapRow(ResultSet rs, int rownum) throws SQLException {
                Post mi = new Post();
                mi.setId(rs.getInt("ID"));
                mi.setTitle(rs.getString("TITLE"));
                mi.setUrl(rs.getString("URL"));
                mi.setDescription(rs.getString("DESCRIPTION"));
                mi.setBanner(rs.getString("BANNER"));
                return mi;
            }
            
        });
        return list;
    }    

    @Override
    public List<MenuItem> getLeftMenu() {
        String sql = "select * from MenuItemn where Position = 'Left' ";
        List<MenuItem> list = jdbcTemplate.query(sql, new RowMapper<MenuItem>(){
 
            public MenuItem mapRow(ResultSet rs, int rownum) throws SQLException {
                MenuItem mi = new MenuItem();
                mi.setId(rs.getInt("ID"));
                mi.setTitle(rs.getString("TITLE"));
                mi.setAction(rs.getString("ACTION"));
                mi.setDescription(rs.getString("DESCRIPTION"));
                return mi;
            }
            
        });
        return list;
    }

    @Override
    public List<Post> getNewPosts() {
        String sql = "select * from Posts where Priority = '1' order by ID";
        List<Post> list = jdbcTemplate.query(sql, new RowMapper<Post>(){
 
            public Post mapRow(ResultSet rs, int rownum) throws SQLException {
                Post mi = new Post();
                mi.setId(rs.getInt("ID"));
                mi.setTitle(rs.getString("TITLE"));
                mi.setUrl(rs.getString("URL"));
                mi.setDescription(rs.getString("DESCRIPTION"));
                mi.setBanner(rs.getString("BANNER"));
                return mi;
            }
            
        });
        return list;
    }

    @Override
    public void addPost(Post post) {
         System.out.println("addPost("+post.getDescription()+")");
    }

    @Override
    public void removePost(long key) {
         System.out.println("removePost("+ key +")");
    }
}
