package core.basesyntax.service;

import java.util.Objects;

public class FruitTransaction {
    private final String type;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(String type, String fruit, int quantity) {
        this.type = type;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity
                && type.equals(that.type)
                && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fruit, quantity);
    }

}
