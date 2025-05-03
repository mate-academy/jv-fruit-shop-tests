package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private String product;
    private Operation operation;
    private int value;

    public Transaction(Operation operation, String product, int value) {
        this.operation = operation;
        this.product = product;
        this.value = value;
    }

    public Transaction() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction that = (Transaction) o;
        return value == that.value && Objects.equals(product, that.product)
                && operation == that.operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, operation, value);
    }
}
