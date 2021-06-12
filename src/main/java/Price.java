import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;


public class Price {
    @JacksonXmlProperty(isAttribute = true, localName = "currency")
    private String currency;
    @JacksonXmlText
    @JacksonXmlProperty(localName = "valueOfProduct")
    private Double value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getValueOfProduct() {
        return value;
    }

    public void setValueOfProduct(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Price{<" +
                "currency='" + currency + '\'' +
                ">, value=" + value +
                '}';
    }
}
