package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandler implements OperationHandler {

    @Override
    public void executeOperation(FruitTransaction transaction) {
        if (transaction.getQuantity() >= 0) {
            Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
        } else {
            throw new RuntimeException("Quantity of fruits should be positive number");
        }
    }
}
