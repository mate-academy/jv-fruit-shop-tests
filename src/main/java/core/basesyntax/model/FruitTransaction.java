package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private String operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(String operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    @Override
    public boolean equals(Object transaction) {
        if (this == transaction) {
            return true;
        }
        if (transaction == null || getClass() != transaction.getClass()) {
            return false;
        }
        FruitTransaction fruitTransaction = (FruitTransaction) transaction;
        return quantity == fruitTransaction.quantity
                && Objects.equals(operation, fruitTransaction.operation)
                && Objects.equals(fruit, fruitTransaction.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
