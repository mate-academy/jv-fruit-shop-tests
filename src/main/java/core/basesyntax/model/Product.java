package core.basesyntax.model;

import core.basesyntax.services.exception.ProductException;

import java.util.Objects;

public class Product {
    private static final String FORMAT = "%s,%s";
    private String name;
    private int count;

    public Product(String name, int count) {
        if (name == null) {
            throw new ProductException("Name parameter can not be null");
        } else if (count <= 0) {
            throw  new ProductException("Quantity parameter can not be less then 0");
        }
        this.count = count;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 0) {
            throw  new ProductException("Quantity parameter can not be less then 0");
        }
        this.count = count;
    }

    public void setName(String name) {
        if (name == null) {
            throw new ProductException("Name parameter can not be null");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return count == product.count && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 71 * result + count;
        result = 71 * result + (this.name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, name, count);
    }
}
