/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.repository;


import fi.dy.potkonen.harjukatu.domain.HarjukatuUtil;
import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author esa
 */
@Repository
public class HarjukatuDAOImpl implements HarjukatuDAO {
        private static Logger logger = LoggerFactory.getLogger("Harjukatu");
    private JdbcTemplate jdbcTemplate;
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<MenuItem> getTopMenu(){
        List<MenuItem> list = new ArrayList<MenuItem>();
        String sql = "{call getTopMenu()}";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement cs = connection.prepareCall(sql);
            ResultSet rs = cs.executeQuery();
            list = HarjukatuUtil.mapMenuItems(rs);
        } catch (SQLException ex) {
            logger.error("Failed sql["+sql+"]", ex);
        }
        return list;
    }    
    public List<Post> getPosts(int level){
        return getPostsByPriority(level);
    }    

    @Override
    public List<MenuItem> getLeftMenu() {
        return null;
   }

    @Override
    public void addPost(Post post) {
        logger.info("addPost("+post.getDescription()+")");
        String sql = "{call newPost(?,?,?,?,?,?)}";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement cs = connection.prepareCall(sql);
            cs.setInt(1, 2); // Priority
            cs.setString(2, post.getTitle());
            cs.setString(3, post.getUrl());
            cs.setString(4, post.getDescription());
            cs.setString(5, post.getBanner());
            cs.setInt(6, 1); // Visibility
            cs.execute();
        } catch (SQLException ex) {
            logger.error("Failed sql["+sql+"]", ex);
        }
    }

    @Override
    public void removePost(long key) {
        logger.info("removePost("+ key +")");
        String sql = "{call delPost("+key+")}";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement cs = connection.prepareCall(sql);
            int affected = cs.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed sql["+sql+"]", ex);
        }
    }

    private List<Post> getPostsByPriority(int priority) {
        List<Post> list = new ArrayList<Post>();
        String sql = "{call getPosts("+priority+")}";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement cs = connection.prepareCall(sql);
            ResultSet rs = cs.executeQuery();
            list = HarjukatuUtil.mapPostItems(rs);
        } catch (SQLException ex) {
            logger.error("Failed sql["+sql+"]", ex);
        }
        return list;
    }
}
