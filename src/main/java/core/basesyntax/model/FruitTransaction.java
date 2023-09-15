package core.basesyntax.model;

import core.basesyntax.strategy.OperationStrategyImpl;

public class FruitTransaction {
    private final Operation operation;
    private final String fruit;
    private final int amount;

    public FruitTransaction(Operation operation, String fruit, int amount) {
        this.operation = operation;
        this.fruit = fruit;
        this.amount = amount;
    }

    public void performTransaction(OperationStrategyImpl strategy) {
        strategy.get(operation).performOperation(fruit, amount);
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
