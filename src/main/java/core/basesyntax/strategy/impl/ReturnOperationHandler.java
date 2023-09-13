package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int oldQuantity = Storage.fruit.get(transaction.getFruit());
        Storage.fruit.put(transaction.getFruit(), oldQuantity + transaction.getQuantity());
    }
}
