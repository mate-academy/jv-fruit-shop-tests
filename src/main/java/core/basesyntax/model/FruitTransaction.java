package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String fruit;
    private int quantity;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) object;
        return quantity == that.quantity
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + ((fruit != null) ? fruit.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
