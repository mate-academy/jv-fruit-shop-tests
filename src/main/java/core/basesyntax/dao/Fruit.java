package core.basesyntax.dao;

import java.util.Objects;

public class Fruit {
    private String name;
    private int quantity;

    public Fruit(String name, int quantity) {
        this.name = name;
        checkQuantity(quantity);
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        checkQuantity(quantity);
        this.quantity = quantity;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return quantity == fruit.quantity && Objects.equals(name, fruit.name);
    }

    private void checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity should be more then 0!");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "FruitDao{"
                + "name='"
                + name
                + '\''
                + ", quantity="
                + quantity
                + '}';
    }
}
