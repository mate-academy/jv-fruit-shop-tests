package core.basesyntax.model;

import core.basesyntax.strategy.Operation;

public class FruitTransaction {
    private Operation operation;
    private String fruit;
    private int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (operation == null) {
            throw new RuntimeException("FruitTransaction wasn't created. Field operation is null");
        }
        if (fruit == null) {
            throw new RuntimeException("FruitTransaction wasn't created. Field fruit is null");
        }
        if (quantity < 0) {
            throw new RuntimeException("FruitTransaction wasn't created. "
                    + "Field quantity less than 0");
        }
        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }
}
