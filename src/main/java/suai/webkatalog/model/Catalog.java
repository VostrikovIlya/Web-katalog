package suai.webkatalog.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

public class Catalog {
    public volatile static Catalog instance;
    private final ConcurrentSkipListSet<Product> products;

    public static Catalog getInstance() {
        if (instance == null) {
            synchronized (Catalog.class) {
                if (instance == null) {
                    instance = new Catalog();
                }
            }
        }
        return instance;
    }

    private Catalog() {
        products = new ConcurrentSkipListSet<>();
    }

    public void loadFile(String filName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filName));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Scanner scanner1 = new Scanner(line);
            Categories categories = null;
            String name = null;
            int price = 0;
            int number = 0;
            if (scanner1.hasNextLine())
                name = scanner1.next();
            if (scanner1.hasNextInt())
                number = scanner1.nextInt();
            if (scanner1.hasNextInt())
                price = scanner1.nextInt();
            if (scanner1.hasNextLine()) {
                categories = Categories.findByName(scanner1.next());
            }
            if (name != null && price != 0 && number != 0) {
                if (categories != null) {
                    products.add(new Product(name, number, price, categories));
                } else {
                    products.add(new Product(name, number, price));
                }
            }
        }
    }

    public synchronized void saveFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Product product : products) {
                Categories categories = product.getCategories();
                if (categories != null) {
                    writer.println(product.getName() + " " + product.getNumber() + " " + product.getPrice() + " " + categories);
                }
                else {
                    writer.println(product.getName() + " " + product.getNumber() + " " + product.getPrice());
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public ConcurrentSkipListSet<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {
       return products.add(product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public boolean removeProduct(String id) {
        return products.removeIf(product -> product.getId().equals(id));
    }

    public boolean containProduct(String id) {
        return products.stream().anyMatch(e -> e.getId().equals(id));
    }

    public boolean containProductName(String name) {
        return products.stream().anyMatch(e -> e.getName().equals(name));
    }

    public Optional<Product> getProduct(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public int getSize(){
        return products.size();
    }
}
