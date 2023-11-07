package model;

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
}
