package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive, but was " + quantity);
        }
        if (fruit == null || fruit.isBlank()) {
            throw new IllegalArgumentException("Fruit must be not null or empty, but was \""
                    + fruit + "\"");
        }
        this.operation = operation;
        this.fruit = fruit;
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
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity
                && operation == that.operation
                && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "FruitTransaction{"
                + "operation= " + operation
                + ", fruit= '" + fruit + '\''
                + ", quantity= " + quantity + '}';
    }
}
