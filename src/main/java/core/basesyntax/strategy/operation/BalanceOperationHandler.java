package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void evaluateTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new NullDataException("Transaction is null!");
        }
        if (transaction.getQuantity() < 0) {
            throw new TransactionQuantityException("You can't balance a negative amount of fruit!");
        }
        Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
