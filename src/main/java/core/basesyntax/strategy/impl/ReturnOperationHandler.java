package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void doActivity(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit value cannot be null");
        }
        int amount = Storage.getOrDefault(fruitTransaction.getFruit(), DEFAULT_VALUE);
        Storage.put(fruitTransaction.getFruit(), amount + fruitTransaction.getQuantity());
    }
}
