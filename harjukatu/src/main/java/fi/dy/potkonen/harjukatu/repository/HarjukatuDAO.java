/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.repository;

import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import java.util.List;

/**
 *
 * @author esa
 */
public interface HarjukatuDAO {

    List<MenuItem> getTopMenu();

    List<MenuItem> getLeftMenu();

    List<Post> getNewPosts();

    List<Post> getAllPosts();
    
    void addPost( Post post );
}
