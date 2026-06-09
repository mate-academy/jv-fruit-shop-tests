package core.basesyntax.service.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;

public class BalanceOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction, Storage storage) {

        if (fruitTransaction == null || storage == null) {
            throw new RuntimeException("input data can not be null");
        }

        storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
