package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Fruit supply amount is negative");
        }
        Storage.storage.put(fruitTransaction.getFruit(),
                Storage.storage.get(fruitTransaction.getFruit())
                        + fruitTransaction.getQuantity());
    }
}
