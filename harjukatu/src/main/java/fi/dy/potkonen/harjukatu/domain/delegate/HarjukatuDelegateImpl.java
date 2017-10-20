/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.domain.delegate;

import static fi.dy.potkonen.harjukatu.domain.Harjukatu.CLAMD;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.FILEPATH;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.ERROR;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MESSAGE.OK;
import static fi.dy.potkonen.harjukatu.domain.Harjukatu.MINUTE;
import fi.dy.potkonen.harjukatu.domain.MenuItem;
import fi.dy.potkonen.harjukatu.domain.Post;
import fi.dy.potkonen.harjukatu.repository.HarjukatuDAO;
import fi.dy.potkonen.harjukatu.domain.Reply;
import fi.solita.clamav.ClamAVClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
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
    public Reply store(String name, byte[] bytes) throws Exception {
        Reply ry = new Reply(OK,"Got store "+name+" request.\n");  
        ClamAVClient cl = new ClamAVClient("localhost", CLAMD, MINUTE);
        cl.ping();
        byte[] reply = cl.scan(bytes);

        if (!ClamAVClient.isCleanReply(reply)) {
            String msg = "ClamAV found something! REJECT";
            ry.setType(ERROR);
            ry.addMessage(msg);
            logger.warn(msg);
        } else { // In memory scan done. Safe enough to save.
            InputStream in = new ByteArrayInputStream(bytes);
            File fl = new File(FILEPATH,name);
            String msg = "File "+fl.getName()+" accepted!";
            FileUtils.copyInputStreamToFile(in,fl);
            ry.addMessage(msg);
            logger.info(msg);
        }
        return ry;
    }
}
