/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.dy.potkonen.harjukatu.web.controller;

import fi.dy.potkonen.harjukatu.domain.delegate.HarjukatuDelegate;
import fi.dy.potkonen.harjukatu.jdbc.SpringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author esa
 */
@Controller
public class FileUploadController {
    private static Logger logger = LoggerFactory.getLogger("Harjukatu");
    HarjukatuDelegate hd;

    public FileUploadController() {
        AnnotationConfigApplicationContext appContext = 
        new AnnotationConfigApplicationContext(SpringConfiguration.class);
        hd = appContext.getBean(HarjukatuDelegate.class);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
        RedirectAttributes redirectAttributes) {
        logger.info("/upload "+file.getOriginalFilename());
        //storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
