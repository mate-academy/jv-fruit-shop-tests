package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object transaction) {
        if (transaction == this) {
            return true;
        }

        if (transaction == null) {
            return false;
        }

        if (transaction.getClass().equals(FruitTransaction.class)) {
            FruitTransaction current = (FruitTransaction) transaction;
            return Objects.equals(this.operation, current.operation)
                    && Objects.equals(this.fruit, current.fruit)
                    && this.quantity == current.quantity;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (operation == null ? 0 : operation.hashCode());
        result = 31 * result + (fruit == null ? 0 : fruit.hashCode());
        result = 31 * result + quantity;
        return result;
    }
}
