package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Fruit fruit;
    private int quantity;
    private String operation;

    public FruitTransaction(Fruit fruit, int quantity, String operation) {
        this.fruit = fruit;
        this.quantity = quantity;
        this.operation = operation;
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
        return quantity == that.quantity && Objects.equals(fruit, that.fruit)
                && Objects.equals(operation, that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, quantity, operation);
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOperation() {
        return operation;
    }
}
