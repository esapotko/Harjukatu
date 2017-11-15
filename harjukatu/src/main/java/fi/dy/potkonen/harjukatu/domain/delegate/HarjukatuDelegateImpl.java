/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain.delegate;

import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.OK;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.SLIDEURL;
import fi.dy.potkonen.harjukatu.domain.HarjukatuUtil;
import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import fi.dy.potkonen.harjukatu.repository.HarjukatuDAO;
import fi.dy.potkonen.harjukatu.domain.Reply;
import fi.dy.potkonen.harjukatu.domain.Slide;
import fi.dy.potkonen.harjukatu.domain.UploadItem;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author esa
 */

 
@Service("harjukatuDelegate")
public class HarjukatuDelegateImpl implements HarjukatuDelegate {
    private static Logger logger = LoggerFactory.getLogger("Harjukatu");
    
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
    public List<Post> getPosts(int level) {
        return getHarjukatuDAO().getPosts(level);
    }

    @Override
    public List<MenuItem> getLeftMenu() {
        return getHarjukatuDAO().getLeftMenu();
    }

    @Override
    public void addPost(Post post) {
        getHarjukatuDAO().addPost(post);
    }

    @Override
    public List<Post> removePost(int key) {
        getHarjukatuDAO().removePost(key);

        return getPosts(2);
    }

    @Override
    public Reply store(String name, String description, byte[] bytes, String ip) throws Exception {
        Reply ry = HarjukatuUtil.store(name, bytes);
        UploadItem it = ry.getItem();
        // Double transaction. File stays if DB fails. Handled manually
        if (ry.getType() == OK) {
            it.setIp(ip);
            it.setDescription(description);
            getHarjukatuDAO().addUploadItem(it);
        }
        return ry;
    }

    private Slide toSlide(UploadItem ui) {
        Slide sl = new Slide(SLIDEURL+ui.getName(), ui.getDescription()));
        return sl;
    }
    
    @Override
    public List<Slide> listSlides() {
        List<UploadItem> items = getHarjukatuDAO().listUploads();
        List<Slide> slides = new ArrayList<Slide>();
        for(UploadItem ui : items) {
            slides.add(toSlide(ui));
        }
        return slides;
    }
}
