package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 14577 on 04/06/17.
 */
@Table
@Entity
public class DealFile {

    @Id
    private String fileName;
    private Date uploadedOn;
    private String status;

    public DealFile(){ }

    public DealFile(String fileName, Date uploadedOn, String status){
        this.fileName = fileName;
        this.uploadedOn = uploadedOn;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(Date uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
