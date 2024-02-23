package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandlerImpl implements OperationHandler {
    private static final int BORDER_POSITIVE = 0;

    @Override
    public void processTransaction(FruitTransaction transaction) {
        if (transaction != null) {
            if (transaction.getQuantity() < BORDER_POSITIVE) {
                throw new RuntimeException("Balance can't be negative quantity!");
            }
            Storage.getStorage().put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
