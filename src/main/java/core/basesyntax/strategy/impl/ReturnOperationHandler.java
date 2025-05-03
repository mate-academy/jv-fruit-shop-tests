package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        int oldQuantity = Storage.fruits.get(fruitTransaction.getFruit());
        Storage.fruits.put(
                fruitTransaction.getFruit(), oldQuantity + fruitTransaction.getQuantity());
    }
}
