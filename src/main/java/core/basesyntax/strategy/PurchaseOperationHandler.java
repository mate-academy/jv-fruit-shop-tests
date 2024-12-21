package core.basesyntax.strategy;

import core.basesyntax.storage.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    public void apply(String fruit, int quantity) {
        if (!Storage.fruits.containsKey(fruit)) {
            Storage.fruits.put(fruit, -quantity);
        } else {
            int currentQuantity = Storage.fruits.get(fruit);
            Storage.fruits.put(fruit, currentQuantity - quantity);
        }
    }
}
