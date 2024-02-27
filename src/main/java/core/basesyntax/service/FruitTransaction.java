package core.basesyntax.service;

import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruitType;
    private final int quantity;

    public FruitTransaction(Operation operation, String fruitType, int quantity) {
        this.operation = operation;
        this.fruitType = fruitType;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruitType() {
        return fruitType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity
                && operation == that.operation
                && Objects.equals(fruitType, that.fruitType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruitType, quantity);
    }
}
