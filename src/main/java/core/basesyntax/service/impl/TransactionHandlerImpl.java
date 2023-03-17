package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class TransactionHandlerImpl implements TransactionHandler {
    private final OperationStrategy operationStrategy;

    public TransactionHandlerImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void parse(List<FruitTransaction> transactionList) {
        for (FruitTransaction transaction : transactionList) {
            try {
                FruitTransaction.Operation operation = transaction.getOperation();
                operationStrategy.get(operation).handle(transaction);
            } catch (NullPointerException e) {
                throw new RuntimeException("Operation not found ", e);
            }
        }
    }
}
