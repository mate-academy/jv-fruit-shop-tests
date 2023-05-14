package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (isTransactionValid(transaction)) {
            FruitStorage.storage.merge(transaction.getProductName(),
                    transaction.getQuantity(), Integer::sum);
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
        if (transaction.getOperation() != FruitTransaction.Operation.RETURN) {
            throw new RuntimeException("Error! Transaction has improper operation type '"
                    + transaction.getOperation() + '\''
                    + " Required '" + FruitTransaction.Operation.RETURN + '\'');
        }
        return true;
    }
}
