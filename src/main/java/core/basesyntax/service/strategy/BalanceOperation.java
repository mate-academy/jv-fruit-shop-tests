package core.basesyntax.service.strategy;

import core.basesyntax.storage.Storage;

public class BalanceOperation implements OperationStrategy {
    @Override
    public void apply(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be negative");
        }
        Storage.fruits.put(fruit, quantity);
    }
}
