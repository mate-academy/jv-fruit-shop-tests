package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperation implements OperationHandler {

    @Override
    public void processWithTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("transaction is empty");
        }
        Storage.getFruits().get(transaction.getFruit());
    }
}
