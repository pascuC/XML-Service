import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public final class ProductOutput implements Comparable<ProductOutput> {
    private String description;
    private String gtin;
    private Price price;
    @JacksonXmlProperty(localName = "orderid")
    private String orderId;

    public ProductOutput() {
    }

    public ProductOutput(String description, String gtin, Price price, String ID) {
        super();
        this.description = description;
        this.gtin = gtin;
        this.price = price;
        this.orderId = ID;
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
        return 0;
    }
}
