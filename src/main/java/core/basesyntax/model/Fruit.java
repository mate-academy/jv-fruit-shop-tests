package core.basesyntax.model;

import java.util.Objects;

public class Fruit {
    private String fruitName;
    private int quantity;

    public Fruit(String name, int quantity) {
        this.fruitName = name;
        this.quantity = quantity;
    }

    public Fruit() {
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = 31 * result + (fruitName == null ? 0 : fruitName.hashCode());
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public boolean equals(Object fruit) {
        if (fruit == this) {
            return true;
        }
        if (fruit == null) {
            return false;
        }
        if (fruit.getClass().equals(Fruit.class)) {
            Fruit current = (Fruit) fruit;
            return Objects.equals(this.fruitName, current.fruitName)
                    && this.quantity == current.quantity;
        }
        return false;
    }
}
