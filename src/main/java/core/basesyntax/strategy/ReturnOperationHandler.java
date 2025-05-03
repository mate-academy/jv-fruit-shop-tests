package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        Storage.fruits.merge(fruit, quantity, Integer::sum);
    }

}
