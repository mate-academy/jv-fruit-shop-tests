package core.basesyntax.model;

import java.util.Objects;

public class Fruit {
    private String type;
    private int quantity;

    public Fruit(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Fruit{"
                + "type='" + type + '\''
                + ", quantity=" + quantity
                + '}';
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
        return quantity == fruit.quantity && Objects.equals(type, fruit.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, quantity);
    }
}
