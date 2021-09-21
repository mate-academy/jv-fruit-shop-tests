package core.basesyntax.dto;

import core.basesyntax.model.Fruit;

public class Transaction {
    private final Operation operation;
    private final Fruit fruit;
    private final int quantity;

    public Transaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "operation: " + operation
                + ", fruit: " + fruit
                + ", quantity: " + quantity
                + "}";
    }

    @Override
    public boolean equals(Object transaction) {
        if (transaction == this) {
            return true;
        }
        if (transaction == null) {
            return false;
        }
        if (transaction.getClass().equals(Transaction.class)) {
            Transaction current = (Transaction) transaction;
            return this.getOperation().equals(current.getOperation())
                    && this.getFruit().equals(current.getFruit())
                    && quantity == current.getQuantity();
        }
        return false;
    }

    public enum Operation {
        BALANCE,
        SUPPLY,
        PURCHASE,
        RETURN
    }
}
