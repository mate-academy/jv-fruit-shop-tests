package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null) {
            throw new RuntimeException("Invalid input transaction data");
        }
        Storage.storage.put(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity());
    }
}
