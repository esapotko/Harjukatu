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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author esa
 */

 
@Service("harjukatuDelegate")
public class HarjukatuDelegateImpl implements HarjukatuDelegate {
    private HarjukatuDAO harjukatuDAO;

    public HarjukatuDAO getHarjukatuDAO() {
        return harjukatuDAO;
    }

    public void setHarjukatuDAO(HarjukatuDAO harjukatuDAO) {
        this.harjukatuDAO = harjukatuDAO;
    }

    @Cacheable("menu")
    public List<MenuItem> getTopMenu() {
        return getHarjukatuDAO().getTopMenu();
    }

    @Override
    public List<Post> getAllPosts() {
        return getHarjukatuDAO().getAllPosts();
    }

    @Override
    public List<MenuItem> getLeftMenu() {
        return getHarjukatuDAO().getLeftMenu();
    }

    @Override
    public List<Post> getNewPosts() {
        return getHarjukatuDAO().getNewPosts();
    }

    @Override
    public void addPost(Post post) {
        getHarjukatuDAO().addPost(post);
    }

    @Override
    public List<Post> removePost(int key) {
        getHarjukatuDAO().removePost(key);
        List<Post> all = getAllPosts();
        // When implemented. Remove following
        for(Post p : all) {
            if(key == p.getId()) {
                all.remove(p);
                break;
            }
        }
        return all;
    }
    
}
