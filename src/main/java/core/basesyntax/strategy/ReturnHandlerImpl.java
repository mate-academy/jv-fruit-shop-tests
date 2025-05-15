package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandlerImpl implements OperationHandler {
    @Override
    public void processTransaction(FruitTransaction transaction) {
        if (transaction != null) {
            Storage.getStorage().compute(transaction.getFruit(),
                    (k, oldQuantity) -> oldQuantity + Math.abs(transaction.getQuantity()));
        }
    }
}
