package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        int currentQuantity = Storage.storage.get(fruit);
        if (currentQuantity < quantity) {
            throw new RuntimeException("Not enough fruit in storage. Fruit: " + fruit
                    + ", current quantity: " + currentQuantity
                    + ", purchase quantity: " + quantity);
        }
        Storage.storage.put(fruit, currentQuantity - quantity);
    }
}
