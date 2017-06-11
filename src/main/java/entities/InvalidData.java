package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by 14577 on 04/06/17.
 */

@Entity
public class InvalidData {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    String row;
    @ManyToOne
    @JoinColumn(name ="fileName")
    DealFile dealFile;

    public InvalidData(){}

    public InvalidData(String row, DealFile fileName){
        this.row = row;
        this.dealFile = fileName;
    }

}
