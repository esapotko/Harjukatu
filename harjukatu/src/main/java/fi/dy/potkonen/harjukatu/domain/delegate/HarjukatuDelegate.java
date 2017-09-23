/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain.delegate;

import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import fi.dy.potkonen.harjukatu.repository.HarjukatuDAO;
import java.util.List;

/**
 *
 * @author esa
 */
public interface HarjukatuDelegate {
    List<MenuItem> getTopMenu();

    List<MenuItem> getLeftMenu();

    List<Post> getAllPosts();
    
    List<Post> getNewPosts();

    HarjukatuDAO getHarjukatuDAO();

    void setHarjukatuDAO(HarjukatuDAO harjukatuDAO);
    
}
