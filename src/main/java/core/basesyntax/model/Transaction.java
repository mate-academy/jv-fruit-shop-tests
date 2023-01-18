package core.basesyntax.model;

public class Transaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public Transaction(String fruit, int quantity) {
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Transaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
