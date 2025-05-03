package core.basesyntax.dto;

import core.basesyntax.model.Fruit;
import java.util.Objects;

public class Transaction {
    private final Transaction.Operation operation;
    private final Fruit fruit;
    private final int quantity;

    public Transaction(Transaction.Operation operation, Fruit fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Transaction.Operation getOperation() {
        return this.operation;
    }

    public Fruit getFruit() {
        return this.fruit;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public enum Operation {
        BALANCE,
        SUPPLY,
        PURCHASE,
        RETURN
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
        return quantity == that.quantity && operation == that.operation && fruit.equals(that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
