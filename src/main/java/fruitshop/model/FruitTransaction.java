package fruitshop.model;

import java.util.Objects;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int amount;

    private FruitTransaction(Operation operation, String fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public static FruitTransaction of(Operation operation, String fruit, int amount) {
        if (amount >= 0) {
            return new FruitTransaction(operation, fruit, amount);
        }

        throw new RuntimeException("Amount cannot be negative");
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FruitTransaction that = (FruitTransaction) o;
        return amount == that.amount
                && operation
                == that.operation
                && Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, amount);
    }
}
