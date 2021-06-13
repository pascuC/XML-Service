import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
/***
 * Wrapper class for deserialization order node of the xml input file
 */
public final class Order {

    @JacksonXmlProperty(isAttribute = true)
    private String created;
    @JacksonXmlProperty(isAttribute = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    private String ID;
    @JacksonXmlProperty(localName = "product")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Product> products;

    public Order() {
    }

    public Order(String createdAt, String ID, List<Product> products) {
        super();
        this.created = createdAt;
        this.ID = ID;
        this.products = products;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
