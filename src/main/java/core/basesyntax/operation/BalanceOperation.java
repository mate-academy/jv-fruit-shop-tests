package core.basesyntax.operation;

import core.basesyntax.storage.Storage;
import core.basesyntax.transactions.FruitTransaction;

public class BalanceOperation implements OperationHandler {

    @Override
    public void operate(FruitTransaction transaction, Storage storage) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }

        if (storage == null) {
            throw new IllegalArgumentException("Storage can't be null");
        }

        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity can't be negative");
        }

        storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
