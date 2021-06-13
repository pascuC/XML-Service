import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.util.Comparator;

/***
 * Wrapper class for deserialization price node of the xml input file
 */
public final class Price implements Comparable<Price> {

    @JacksonXmlProperty(isAttribute = true)
    private String currency;
    @JacksonXmlText
    private Double value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValueOfProduct(Double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Price o) {
        return Comparator.comparing(Price::getValue)
                .compare(this, o);
    }
}
