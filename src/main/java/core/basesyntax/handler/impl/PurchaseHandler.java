package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Invalid Value! Type of Fruit is null!");
        }
        int beforePurchasing = Storage.STORAGE.get(fruitTransaction.getFruit());
        if (beforePurchasing < fruitTransaction.getQuantity()) {
            throw new RuntimeException("We`re apologizing, only: "
                    + beforePurchasing + " items of:" + fruitTransaction.getFruit()
                    + ", left in the storage.");
        }
        if (fruitTransaction.getQuantity() == 0) {
            throw new RuntimeException("You cannot purchase "
                    + 0 + "items of " + fruitTransaction.getFruit()
                    + ". Please change quantity.");
        }
        int afterPurchasing = beforePurchasing - fruitTransaction.getQuantity();
        Storage.STORAGE.put(fruitTransaction.getFruit(), afterPurchasing);
    }
}
