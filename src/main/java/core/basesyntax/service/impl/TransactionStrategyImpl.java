package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.TransactionStrategy;
import java.util.List;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<Transaction.Operation, TransactionHandler> operationStrategies;

    public TransactionStrategyImpl(
            Map<Transaction.Operation, TransactionHandler> operationStrategies) {
        this.operationStrategies = operationStrategies;
    }

    @Override
    public void process(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new RuntimeException("No transactions present");
        }
        for (Transaction transaction : transactions) {
            TransactionHandler strategy = getHandler(transaction.getOperation());
            if (strategy == null) {
                throw new RuntimeException("transaction is missing");
            }
            strategy.updateStorage(transaction.getFruitName(), transaction.getQuantity());
        }
    }

    private TransactionHandler getHandler(Transaction.Operation operation) {
        return operationStrategies.get(operation);
    }
}
