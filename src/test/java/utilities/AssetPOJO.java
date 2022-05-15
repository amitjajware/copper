package utilities;

import org.apache.juneau.annotation.Beanc;

import java.math.BigDecimal;

public class AssetPOJO {

    private Long id;
    private String name;
    private String currency;
    private Short year;
    private BigDecimal value;

    @Beanc(properties = "id,name,currency,year,value")
    public AssetPOJO(Long id, String name, String currency, Short year, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.year = year;
        this.value = value;
    }



    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
