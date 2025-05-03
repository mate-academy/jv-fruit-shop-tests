package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid transaction: negative quantity");
        }
        if (fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null) {
            throw new NullPointerException("Invalid transaction, something is null!");
        }
        Storage.storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
