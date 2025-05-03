package core.basesyntax.dto;

import core.basesyntax.model.Fruit;
import java.util.Objects;

public class Transaction {
    private final String operation;
    private final Fruit fruit;
    private final int quantity;

    public Transaction(String operation, Fruit fruit, int quality) {
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quality;
    }

    public String getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public Integer getQuantity() {
        return quantity;
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
        return quantity == that.quantity
                && Objects.equals(operation, that.operation)
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }
}
