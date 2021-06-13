import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/***
 * Wrapper class for serialization root node of the xml output file
 */
@JacksonXmlRootElement(localName = "products")
public final class ProductsOutput implements Comparable<ProductsOutput> {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "product")
    private List<ProductOutput> products;

    public ProductsOutput() {
    }

    public ProductsOutput(List<ProductOutput> productOutputs) {
        super();
        this.products = productOutputs;
    }

    public List<ProductOutput> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOutput> products) {
        this.products = products;
    }

    @Override
    public int compareTo(ProductsOutput o) {
        return  0;
    }
}
