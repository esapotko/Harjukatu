/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.jdbc;

import fi.dy.potkonen.harjukatu.domain.delegate.HarjukatuDelegate;
import fi.dy.potkonen.harjukatu.domain.delegate.HarjukatuDelegateImpl;
import fi.dy.potkonen.harjukatu.repository.HarjukatuDAO;
import fi.dy.potkonen.harjukatu.repository.HarjukatuDAOImpl;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author esa
 */
@Configuration
public class SpringConfiguration {
    @Value("#{environment['DBUSER']}")
    private String user;    
    @Value("#{environment['DBPASS']}")
    private String password;    
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //MySQL database 
        System.out.println("*********** Using user["+user+"] **************");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/potkonen?useSSL=false");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public HarjukatuDAO harjukatuDAO(){
        HarjukatuDAOImpl dao = new HarjukatuDAOImpl();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }
    @Bean
    public HarjukatuDelegate harjukatuDelegate(){
        HarjukatuDelegate delegate = new HarjukatuDelegateImpl();
        delegate.setHarjukatuDAO(harjukatuDAO());
        return delegate;
    }
}
