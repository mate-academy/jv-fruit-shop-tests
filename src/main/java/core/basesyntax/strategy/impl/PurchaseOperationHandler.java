package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void calculate(FruitTransaction transaction) {
        Storage.fruits.compute(transaction.getFruit(),
                (k, v) -> v == null ? 1 : v - transaction.getQuantity());
    }
}
