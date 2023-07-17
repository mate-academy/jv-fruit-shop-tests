package core.basesyntax.model;

import core.basesyntax.service.impl.ErrorDataException;

public class FruitTransaction {
    protected String fruit;
    protected int quantity;
    private Operation operation;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        this.operation = operation;
        this.fruit = fruit;
        if (quantity < 0) {
            throw new ErrorDataException("Quantity couldn't be negative ");
        } else {
            this.quantity = quantity;
        }
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

