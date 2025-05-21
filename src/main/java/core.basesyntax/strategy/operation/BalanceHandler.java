package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    private void validateTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Fruit transaction cannot be null");
        }
        if (transaction.getFruit() == null || transaction.getFruit().isEmpty()) {
            throw new IllegalArgumentException("Fruit name cannot be null or empty");
        }
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("Fruit quantity must be positive");
        }
    }

    @Override
    public void getOperation(FruitTransaction transaction) {
        validateTransaction(transaction);
        Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
