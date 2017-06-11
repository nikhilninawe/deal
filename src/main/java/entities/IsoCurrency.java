package entities;

/**
 * Created by 14577 on 03/06/17.
 */
public class IsoCurrency {

    private String country;
    private String currencyCode;

    public IsoCurrency(String country, String currencyCode){
        this.country = country;
        this.currencyCode = currencyCode;
    }

    public IsoCurrency(String currencyCode){
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IsoCurrency that = (IsoCurrency) o;

        return currencyCode != null ? currencyCode.equals(that.currencyCode) : that.currencyCode == null;
    }

    @Override
    public int hashCode() {
        return currencyCode != null ? currencyCode.hashCode() : 0;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
