import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import suai.webkatalog.model.Basket;
import suai.webkatalog.model.Product;

public class TestBasket {
    Basket basket = new Basket();
    @Before
    public void setBasket(){
        basket.addProduct(new Product("Book",5,10));
        basket.addProduct(new Product("Mouse", 3, 20));
        basket.addProduct(new Product("Phone", 50,22));
    }
    @Test
    public void testGetSize(){
        Assertions.assertEquals(3,basket.size());
    }

    @Test
    public void testContainsProduct(){
        Product first = basket.getProducts().first();
        Assertions.assertTrue(basket.containsProduct(first.getId()));
        basket.removeProduct(first.getId());
        Assertions.assertFalse(basket.containsProduct(first.getId()));
    }
    @Test
    public void testRemoveProduct() {
        Product first = basket.getProducts().first();
        Product tmp = basket.removeProduct(first.getId()).get();
        Assertions.assertEquals(tmp,first);
    }
}
