/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain.delegate;

import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.ERROR;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.OK;
import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import fi.dy.potkonen.harjukatu.repository.HarjukatuDAO;
import fi.dy.potkonen.harjukatu.web.controller.Reply;
import fi.solita.clamav.ClamAVClient;
import java.io.InputStream;
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
        List<Post> all = getPosts(2);
        return all;
    }

    @Override
    public Reply store(InputStream is) {
        Reply ry = new Reply(OK,"");  
        ClamAVClient cl = new ClamAVClient("localhost", 3310);
        byte[] reply;
        try {
            reply = cl.scan(is);
        } catch (Exception e) {
            throw new RuntimeException("Could not scan the input", e);
        }
        if (!ClamAVClient.isCleanReply(reply)) {
            String msg = "ClamAV Aaargh. Something was found";
            ry = new Reply(ERROR,msg);
            logger.warn(msg);
        }
        return ry;
    }
}
