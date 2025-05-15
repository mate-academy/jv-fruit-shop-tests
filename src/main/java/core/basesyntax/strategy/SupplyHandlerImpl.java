package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandlerImpl implements OperationHandler {
    @Override
    public void processTransaction(FruitTransaction transaction) {
        if (transaction != null) {
            Storage.getStorage().compute(transaction.getFruit(),
                    (k, oldQuantity) -> oldQuantity + transaction.getQuantity());
        }
    }
}
