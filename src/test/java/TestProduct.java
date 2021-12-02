import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import suai.webkatalog.model.Product;

public class TestProduct {
    private Product product;
    private Product product1;

    @Before
    public void setProduct() {
        product = new Product("Book", 5, 10);
        product1 = new Product("Book", 5, 10);
    }

    @Test
    public void testCompareTo() {
        int comp = product.compareTo(product1);
        Assertions.assertEquals(0, comp);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Product productRes = product.clone();
        Assertions.assertEquals(product, productRes);
    }
}
