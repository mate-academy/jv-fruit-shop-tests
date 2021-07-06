package core.basesyntax.dto;

import core.basesyntax.model.Operation;
import java.util.Objects;

public class Transaction {
    private Operation operation;
    private String name;
    private int quantity;

    public Transaction(Operation operation, String name, int quantity) {
        this.operation = operation;
        this.name = name;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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
        return quantity == that.quantity && operation == that.operation
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, name, quantity);
    }

    @Override
    public String toString() {
        return  "Transaction{" + "operation=" + operation
               + ", name='" + name + '\''
                + ", quantity=" + quantity
                + '}';
    }
}
