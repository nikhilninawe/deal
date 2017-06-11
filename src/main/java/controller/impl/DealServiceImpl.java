package controller.impl;

import controller.DealService;
import manager.DealManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class DealServiceImpl implements DealService {

    private static Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);

    @Autowired
    DealManager dealManager;

    @Override
    public String uploadFile(@RequestParam("file") MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        long startTime = System.currentTimeMillis();

        try {
            dealManager.processFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        }catch (Exception ex){
            System.out.println("Got exception " + ex);
            redirectAttributes.addFlashAttribute("message",
                    "Error while importing " + file.getOriginalFilename() + ". Error : " + ex.getMessage());
        }
        logger.info("Time take {} ms", (System.currentTimeMillis() - startTime));
        return "redirect:/deal";
    }

    @Override
    public String getFileDetails(String fileName, RedirectAttributes redirectAttributes) {
        String data  = dealManager.enquire(fileName);
        redirectAttributes.addFlashAttribute("fileResponse",
                data);
        return "redirect:/deal";
    }

    public String getUploadPage(){
        return "uploadFile";
    }
}
