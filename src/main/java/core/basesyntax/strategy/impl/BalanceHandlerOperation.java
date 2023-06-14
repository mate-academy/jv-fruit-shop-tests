package core.basesyntax.strategy.impl;

import core.basesyntax.db.DataStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandlerOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        DataStorage.fruitStorageMap.put(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity());
    }
}
