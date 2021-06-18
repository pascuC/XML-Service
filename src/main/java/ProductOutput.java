import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Comparator;

/***
 * Wrapper class for serialization product node of the xml output file
 */
public class ProductOutput implements Comparable<ProductOutput> {
    @JsonIgnore
    private String orderedAt;
    private String description;
    private String gtin;
    private Price price;
    @JacksonXmlProperty(localName = "orderid")
    private String orderId;

    public ProductOutput() {
    }

    public ProductOutput(String order, String description, String gtin, Price price, String ID) {
        super();
        this.orderedAt = order;
        this.description = description;
        this.gtin = gtin;
        this.price = price;
        this.orderId = ID;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int compareTo(ProductOutput o) {
        return Comparator.comparing(ProductOutput::getOrderedAt)
                .thenComparing(ProductOutput::getPrice)
                .compare(this, o);
    }

}
