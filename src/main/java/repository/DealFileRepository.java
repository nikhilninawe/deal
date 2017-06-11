package repository;

import entities.DealFile;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 14577 on 04/06/17.
 */
public interface DealFileRepository extends CrudRepository<DealFile, String> {

    DealFile findByFileName(String fileName);
}
