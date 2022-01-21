package core.basesyntax.model;

import java.util.Objects;

public class FruitRecord {
    private final Operation operation;
    private final Fruit fruit;
    private final int amount;

    public FruitRecord(Operation operation, Fruit fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FruitRecord{"
                + "operation=" + operation
                + ", Fruit='" + fruit + '\''
                + ", amount=" + amount
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FruitRecord record = (FruitRecord) o;
        return amount == record.amount
                && operation == record.operation
                && Objects.equals(fruit, record.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }

    public enum Operation {
        BALANCE,
        SUPPLY,
        PURCHASE,
        RETURN;
    }
}
