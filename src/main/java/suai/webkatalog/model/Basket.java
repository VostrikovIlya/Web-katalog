package suai.webkatalog.model;

import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

public class Basket {
    private final ConcurrentSkipListSet<Product> products;

    public Basket() {
        products = new ConcurrentSkipListSet<>();
    }

    public ConcurrentSkipListSet<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Optional<Product> removeProduct(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                products.remove(product);
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public boolean containsProduct(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Product> getProduct(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public int size() {
        return products.size();
    }
}
