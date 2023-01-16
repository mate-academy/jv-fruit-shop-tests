package core.basesyntax.db.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new NullPointerException("You can't return product that we don't sell");
        }
        int previousQuantity = Storage.getAll().get(transaction.getFruit());
        Storage.getAll().put(transaction.getFruit(),
                previousQuantity + transaction.getQuantity());
    }
}
