package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String fruit;
    private int quantity;
    private Operation operation;

    private FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public static FruitTransaction of(Operation operation, String fruit, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be less than 0");
        }
        return new FruitTransaction(operation, fruit, quantity);
    }

    public String getFruit() {
        return fruit;
    }

    public void subtract(int quantity) {
        if (quantity > this.quantity) {
            throw new RuntimeException("Cannot sell fruits more than in stock");
        }
        this.quantity -= quantity;
    }

    public void add(int quantity) {
        this.quantity += quantity;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new RuntimeException("Quantity cannot be less than 0");
        }
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
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
        return quantity == that.quantity
                && Objects.equals(fruit, that.fruit) && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, quantity, operation);
    }
}
