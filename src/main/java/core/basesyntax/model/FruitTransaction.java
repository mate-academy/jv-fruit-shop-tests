package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String name;
    private int quantity;
    private Operation operation;

    public FruitTransaction() {
    }

    public FruitTransaction(String name, int quantity, Operation operation) {
        this.name = name;
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
        return quantity == that.quantity && Objects.equals(name, that.name)
                && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, operation);
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
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
