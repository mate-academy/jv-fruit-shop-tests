package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException("Invalid amount of Fruits! Amount: "
                    + fruitTransaction.getQuantity());
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit Type is null!");
        }
        int beforeSupplying = Storage.STORAGE.get(fruitTransaction.getFruit());
        int afterSupplying = beforeSupplying + fruitTransaction.getQuantity();
        Storage.STORAGE.put(fruitTransaction.getFruit(), afterSupplying);
    }
}
