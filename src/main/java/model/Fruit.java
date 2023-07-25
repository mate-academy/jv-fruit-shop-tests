package model;

import java.util.Objects;

public class Fruit {
    private final String name;
    private final Integer quantity;

    public Fruit(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Fruit)) {
            return false;
        }
        Fruit fruit = (Fruit) obj;
        return Objects.equals(getName(), fruit.getName())
                && Objects.equals(getQuantity(), fruit.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuantity());
    }
}
