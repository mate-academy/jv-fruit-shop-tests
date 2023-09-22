package core.basesyntax.service.strategy;

import core.basesyntax.storage.Storage;

public abstract class AddOperation implements OperationStrategy {
    private static final int MAX_STORAGE_CAPACITY = Integer.MAX_VALUE;

    @Override
    public void apply(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Negative amount of fruits was provided: " + quantity);
        }
        Storage.fruits.merge(fruit, quantity, this::sumFruits);
    }

    private int sumFruits(int oldValue, int income) {
        if (oldValue > MAX_STORAGE_CAPACITY - income) {
            throw new IllegalArgumentException(
                    "Result of operation overflows storage max capacity. "
                            + "Provided quantity: " + income
                            + ", amount in Storage: " + oldValue
                            + ", max Storage capacity: " + MAX_STORAGE_CAPACITY);
        }
        return oldValue + income;
    }
}
