package controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RequestMapping("/deal")
public interface DealService {

    @PostMapping("/upload")
	public String uploadFile(MultipartFile file,
                           RedirectAttributes redirectAttributes);

    @GetMapping("/details")
    public String getFileDetails(String fileName,
                               RedirectAttributes redirectAttributes);

    @GetMapping
    public String getUploadPage();
	
}
