import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import suai.webkatalog.model.Catalog;
import suai.webkatalog.model.Product;
import suai.webkatalog.model.Users;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

public class TestCatalog {
    Catalog catalog = Catalog.getInstance();

    @Before
    public void setCatalog() throws NoSuchFieldException, IllegalAccessException {
        Field instance = Users.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        catalog = Catalog.getInstance();
        catalog.addProduct(new Product("Book", 5, 20));
        catalog.addProduct(new Product("Notebook", 2, 33));
        catalog.addProduct(new Product("Monitor", 21, 900));
    }

    @Test
    public void testContainProduct() {
        Assertions.assertTrue(catalog.containProductName("Book"));
        Assertions.assertFalse(catalog.containProductName("Oook"));
        Assertions.assertTrue(catalog.containProduct(catalog.getProducts().first().getId()));
        Assertions.assertFalse(catalog.containProduct(catalog.getProducts().first().getId() + "000"));
    }

    @Test
    public void testEmpty() {
        Assertions.assertFalse(catalog.isEmpty());
        catalog.getProducts().clear();
        Assertions.assertTrue(catalog.isEmpty());
    }

    @Test
    public void testLoadFile() throws FileNotFoundException {
        catalog.getProducts().clear();
        catalog.loadFile("src/test/java/data/catalog.txt");
        Assertions.assertEquals(3, catalog.getSize());
    }

    @Test
    public void testSaveFile() throws FileNotFoundException {
        String fileName = "src/test/java/data/catalog.txt";
        catalog.saveFile(fileName);
        catalog.getProducts().clear();
        catalog.loadFile(fileName);
        Assertions.assertEquals(3, catalog.getSize());
    }
}
