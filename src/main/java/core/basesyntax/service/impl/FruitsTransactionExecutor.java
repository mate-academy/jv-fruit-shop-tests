package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.strategy.OperationStrategy;

public class FruitsTransactionExecutor implements TransactionExecutor {
    private final OperationStrategy operationStrategy;

    public FruitsTransactionExecutor(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void executeTransaction(Transaction transaction) {
        operationStrategy.getOperationHandler(transaction.getOperationType())
                .processData(transaction.getName(), transaction.getQuantity());
    }
}
