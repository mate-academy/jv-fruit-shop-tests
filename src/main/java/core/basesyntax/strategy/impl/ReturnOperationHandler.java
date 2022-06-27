package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void applyChanges(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Incorect numbers of fruits for return");
        }
        if (fruitTransaction.getNameOfFruit() == null) {
            throw new RuntimeException("Put the name of fruit for return");
        }
        int fruitQuantity = Storage.storage.get(fruitTransaction.getNameOfFruit());
        Storage.storage.put(fruitTransaction.getNameOfFruit(),
                fruitQuantity + fruitTransaction.getQuantity());
    }
}
