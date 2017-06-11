package manager;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 14577 on 03/06/17.
 */
public interface DealManager {

    void processFile(MultipartFile file) throws Exception;

    String enquire(String fileName);

}
