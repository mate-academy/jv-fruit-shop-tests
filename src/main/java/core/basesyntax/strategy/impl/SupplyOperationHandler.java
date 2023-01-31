package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void calculate(Transaction transaction) {
        int oldQuantity = Storage.fruitsStorage.getOrDefault(transaction.getFruit(), 0);
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Invalid supply data of fruit: " + transaction.getFruit());
        }
        Storage.fruitsStorage.put(transaction.getFruit(),oldQuantity + transaction.getQuantity());
    }
}
