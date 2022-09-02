package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private Operation operation;
    private Fruit fruit;
    private int quantity;

    public Transaction() {
    }

    public Transaction(Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public Transaction setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public Transaction setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Transaction setFruit(Fruit fruit) {
        this.fruit = fruit;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "operation=" + operation + ", fruit=" + fruit
                + ", quantity=" + quantity + '}';
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
