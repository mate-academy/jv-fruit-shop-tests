package core.basesyntax.service;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class TransactionProcessorImpl implements TransactionProcessor {
    private final OperationStrategy operationStrategy;

    public TransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<Transaction> transactions) {
        if (transactions == null || transactions.size() == 0) {
            return;
        }
        transactions.forEach(transaction ->
                operationStrategy.get(transaction.getOperation()).handle(transaction));
    }
}
