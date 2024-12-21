package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        int currentQuantity = Storage.fruits.getOrDefault(fruit, 0);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        Storage.fruits.put(fruit, currentQuantity + quantity);
    }

}
