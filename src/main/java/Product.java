/***
 * Wrapper class for deserialization product node of the xml input file
 */
public class Product {

    private String description;
    private String gtin;
    private Price price;
    private String supplier;

    public Product() {
    }

    public Product(String description, String gtin, Price price, String supplier) {
        super();
        this.description = description;
        this.gtin = gtin;
        this.price = price;
        this.supplier = supplier;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

}