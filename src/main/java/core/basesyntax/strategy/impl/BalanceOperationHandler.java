package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (isTransactionValid(transaction)) {
            FruitStorage.storage.put(transaction.getProductName(), transaction.getQuantity());
        }
    }

    private boolean isTransactionValid(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Error! Transaction can't be with negative value."
                    + " Actual value is '" + transaction.getQuantity() + '\'');
        }
        if (transaction.getProductName().isBlank()) {
            throw new RuntimeException("Error! Transaction can't be with empty product name");
        }
        if (transaction.getOperation() != FruitTransaction.Operation.BALANCE) {
            throw new RuntimeException("Error! Transaction has improper operation type '"
                    + transaction.getOperation() + '\''
                    + " Required '" + FruitTransaction.Operation.BALANCE + '\'');
        }
        return true;
    }
}
