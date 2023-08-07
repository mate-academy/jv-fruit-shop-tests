package core.basesyntax.model;

import java.util.Objects;

public class Transaction {
    private Operation operationType;
    private String name;
    private Integer quantity;

    public Transaction(Operation operationType, String name, Integer quantity) {
        this.operationType = operationType;
        this.name = name;
        this.quantity = quantity;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation operationType) {
        this.operationType = operationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "operationType=" + operationType
                + ", name='" + name + '\''
                + ", quantity=" + quantity
                + '}';
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
        return operationType == that.operationType
                && Objects.equals(name, that.name)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationType, name, quantity);
    }
}
