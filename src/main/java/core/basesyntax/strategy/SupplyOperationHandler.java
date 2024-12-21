package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (Storage.fruits.containsKey(fruit)) {
            Storage.fruits.put(fruit, Storage.fruits.get(fruit) + quantity);
        } else {
            Storage.fruits.put(fruit, quantity);
        }
    }
}
