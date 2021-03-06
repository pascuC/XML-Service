import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/***
 * Wrapper class for deserialization root node of the xml input file
 */
@JacksonXmlRootElement(localName = "orders")
public class Orders {

    @JacksonXmlProperty(localName = "order")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
