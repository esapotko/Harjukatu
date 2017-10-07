/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.web.controller;

import fi.dy.potkonen.harjukatu.domain.Harjukatu;
import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import fi.dy.potkonen.harjukatu.domain.delegate.HarjukatuDelegate;
import fi.dy.potkonen.harjukatu.jdbc.SpringConfiguration;
import io.swagger.annotations.Api;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author esa
 */
@RestController

@Api(value="harjukatu", description="Operations pertaining to Harjukatu Site")
public class HarjukatuController {
    private static Logger logger = LoggerFactory.getLogger("Harjukatu");
    HarjukatuDelegate hd;

    public HarjukatuController() {
        AnnotationConfigApplicationContext appContext = 
        new AnnotationConfigApplicationContext(SpringConfiguration.class);
        hd = appContext.getBean(HarjukatuDelegate.class);
    }
    
    @RequestMapping(value="/api/menu",
        method={RequestMethod.GET})
    List<MenuItem> menu() {
        logger.info("menu "+hd.getTopMenu()+")");
        
        return hd.getTopMenu();
    }
    
    @RequestMapping(value="/api/posts",
        method={RequestMethod.GET})
    List<Post> posts() {
        return hd.getAllPosts();
    }
    
    @RequestMapping(value="/api/newposts",
        method={RequestMethod.GET})
    List<Post> newposts() {
        logger.info("newposts"+hd.getNewPosts()+")");
        
        return hd.getNewPosts();
    }
    
    @RequestMapping(value="/api/outerposts",
        method={RequestMethod.GET})
    List<Post> outerposts() {
        logger.info("outerposts"+hd.getOutPosts()+")");
        
        return hd.getOutPosts();
    }

    @RequestMapping(value="/api/posts/{index}/del",
        method={RequestMethod.POST,RequestMethod.GET})
    List<Post> delPost(@PathVariable int index) {
        logger.info("delPost("+index+")");

        return hd.removePost(index);
    }
    
    @RequestMapping(value = "/api/add", method = RequestMethod.POST)	
    public Reply newPost( @RequestBody Post post )   {		
        logger.info("addPost("+post+")");

        hd.addPost(post);
        return new Reply(Harjukatu.MESSAGE.OK, "Post With : " + post.getDescription());
    }
}
