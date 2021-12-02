package suai.webkatalog.model;

import java.util.Objects;
import java.util.UUID;

public class Product implements Comparable<Product>, Cloneable{
    private String name;
    private Integer number;
    private Integer price;
    private String id;
    private Categories categories;


    public Product(String name, int number, int price) {
        this.id = UUID.randomUUID().toString().replace("-","");
        this.name = name;
        this.number = number;
        this.price = price;
    }
    public Product(String name, int number, int price, String id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
    }
    public Product(String name, int number, int price, Categories categories) {
        this.id = UUID.randomUUID().toString().replace("-","");
        this.categories = categories;
        this.name = name;
        this.number = number;
        this.price = price;
    }
    public Product(String name, int number, int price, String id, Categories categories) {
        this.id = id;
        this.categories = categories;
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    @Override
    public Product clone() throws CloneNotSupportedException {
        Product clone = (Product) super.clone();
        clone.name = this.name;
        clone.number = this.number;
        clone.price = this.price;
        clone.id = this.id;
        return clone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return name.compareTo(o.name) + price.compareTo(o.price) + number.compareTo(o.number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(number, product.number) && Objects.equals(price, product.price) && Objects.equals(id, product.id) && categories == product.categories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number, price, id, categories);
    }
}
