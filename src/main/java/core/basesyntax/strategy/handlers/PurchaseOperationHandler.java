package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity can't be negative");
        }
        if (Storage.get(fruit) == null) {
            throw new RuntimeException(
                    "Fruit " + fruit + " is not exist in Storage");
        }
        if (Storage.get(fruit) < quantity) {
            throw new RuntimeException(
                    "Not enough fruits in storage. Remained only " + Storage.get(fruit));
        }
        Storage.put(fruit, Storage.get(fruit) - quantity);
    }
}
