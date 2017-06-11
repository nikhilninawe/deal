package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 14577 on 03/06/17.
 */

@Table(name = "currency_deal_count")
@Entity
public class CurrencyDealCount {


    @Id
    private String currencyCode;
    private Long dealCount;

    public CurrencyDealCount(){}

    public CurrencyDealCount(String currencyCode, Long dealCount){
        this.currencyCode = currencyCode;
        this.dealCount = dealCount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Long getDealCount() {
        return dealCount;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setDealCount(Long dealCount) {
        this.dealCount = dealCount;
    }

    @Override
    public String toString() {
        return "CurrencyDealCount{" +
                "currencyCode='" + currencyCode + '\'' +
                ", dealCount=" + dealCount +
                '}';
    }
}
