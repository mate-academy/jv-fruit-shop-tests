package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException("Invalid amount of Fruits! Amount: "
                    + fruitTransaction.getQuantity());
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Invalid Value! Type of Fruit is null!");
        }
        int totalQuantityOfFruit = Storage.STORAGE.get(fruitTransaction.getFruit());
        int returnedQuantity = fruitTransaction.getQuantity() + totalQuantityOfFruit;
        Storage.STORAGE.put(fruitTransaction.getFruit(), returnedQuantity);
    }
}
