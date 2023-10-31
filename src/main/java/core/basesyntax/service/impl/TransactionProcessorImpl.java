package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class TransactionProcessorImpl implements TransactionProcessor {
    private final OperationStrategy operationStrategy;

    public TransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransaction(List<Transaction> transactionsList) {
        for (Transaction transaction : transactionsList) {
            OperationHandler operationHandler = operationStrategy
                    .get(transaction.getFruitOperationType());
            operationHandler.doTransaction(transaction.getFruitName(), transaction.getFruitValue());
        }
    }
}
