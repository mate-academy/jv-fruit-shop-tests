package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    public void apply(String fruit, int quantity) {
        if (!Storage.fruits.containsKey(fruit)) {
            throw new IllegalArgumentException("Fruit does not exist in storage");
        }
        Integer currentQuantity = Storage.fruits.get(fruit);
        if (currentQuantity == null || currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough stock for " + fruit);
        }
        Storage.fruits.put(fruit, currentQuantity - quantity);
    }
}

