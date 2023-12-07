package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException("Invalid amount of Fruits! Amount: "
                    + fruitTransaction.getQuantity());
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("FruitTransaction Type is null: "
                    + fruitTransaction.getFruit());
        }
        Storage.STORAGE.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
