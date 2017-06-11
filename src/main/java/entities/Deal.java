package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class Deal {

    @Id
	@GeneratedValue
	Long id;

	private String dealId;
	private String orderingCurrency;
	private String toCurrency;
	private Date timestamp;
	private Double amountInOrderingCurrency;
    @ManyToOne
    @JoinColumn(name ="fileName")
    private DealFile dealFile;
	public Deal(){}

	public Deal(String dealId, String orderingCurrency, String toCurrency, Date timestamp, Double amountInOrderingCurrency, DealFile dealFile){
	    this.dealId = dealId;
	    this.orderingCurrency = orderingCurrency;
	    this.toCurrency = toCurrency;
	    this.timestamp = timestamp;
	    this.amountInOrderingCurrency = amountInOrderingCurrency;
	    this.dealFile = dealFile;
	}

	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getOrderingCurrency() {
		return orderingCurrency;
	}
	public void setOrderingCurrency(String orderingCurrency) {
		this.orderingCurrency = orderingCurrency;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Double getAmountInOrderingCurrency() {
		return amountInOrderingCurrency;
	}
	public void setAmountInOrderingCurrency(Double amountInOrderingCurrency) {
		this.amountInOrderingCurrency = amountInOrderingCurrency;
	}

    @Override
    public String toString() {
        return "Deal{" +
                ", dealId='" + dealId + '\'' +
                ", orderingCurrency='" + orderingCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", timestamp=" + timestamp +
                ", amountInOrderingCurrency=" + amountInOrderingCurrency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deal deal = (Deal) o;

        return dealId != null ? dealId.equals(deal.dealId) : deal.dealId == null;
    }

    @Override
    public int hashCode() {
        return dealId != null ? dealId.hashCode() : 0;
    }
}
