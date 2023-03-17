package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void doActivity(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit value cannot be null");
        }
        Storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
