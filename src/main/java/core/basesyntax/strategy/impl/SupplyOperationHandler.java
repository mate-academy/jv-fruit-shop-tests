package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        Storage.fruits.put(transaction.getFruit(),
                Storage.fruits.containsKey(transaction.getFruit())
                ? Storage.fruits.get(transaction.getFruit()) + transaction.getQuantity()
                : transaction.getQuantity());
    }
}
