package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import java.util.Objects;

public class TransactionDto {
    private final Fruit fruit;
    private final int amount;
    private final String operationType;

    public TransactionDto(String operationType, Fruit fruit, int amount) {
        this.fruit = fruit;
        this.amount = amount;
        this.operationType = operationType;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmount() {
        return amount;
    }

    public String getOperationType() {
        if (operationType == null) {
            throw new RuntimeException("Illegal operation");
        }
        return operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionDto that = (TransactionDto) o;
        return amount == that.amount
                && Objects.equals(fruit, that.fruit)
                && Objects.equals(operationType, that.operationType);
    }
}
