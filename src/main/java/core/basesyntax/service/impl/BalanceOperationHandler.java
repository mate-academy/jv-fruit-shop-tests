package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        //Storage.fruit.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        Storage.addFruits(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
