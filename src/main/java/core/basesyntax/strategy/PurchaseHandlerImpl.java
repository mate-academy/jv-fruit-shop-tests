package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandlerImpl implements OperationHandler {
    @Override
    public void processTransaction(FruitTransaction transaction) {
        if (transaction != null) {
            Integer oldQuantity = Storage.getStorage().get(transaction.getFruit());
            Storage.getStorage().put(transaction.getFruit(),
                    oldQuantity - Math.abs(transaction.getQuantity()));
        }
    }
}
