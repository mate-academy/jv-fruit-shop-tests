package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {

    @Override
    public void operateTransaction(FruitTransaction transaction, Storage storage) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Operation can't be negative.");
        }
        int oldQuantity = storage.get(transaction.getFruit());
        storage.put(transaction.getFruit(), oldQuantity + transaction.getQuantity());
    }
}
