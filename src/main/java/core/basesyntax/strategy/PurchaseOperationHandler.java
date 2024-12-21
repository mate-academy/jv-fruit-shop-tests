package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (!Storage.fruits.containsKey(fruit)) {
            throw new IllegalArgumentException("Fruit not available in storage: " + fruit);
        }
        int currentQuantity = Storage.fruits.get(fruit);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough " + fruit + " in storage. Available: " + currentQuantity);
        }
        Storage.fruits.put(fruit, currentQuantity - quantity);
    }
}
