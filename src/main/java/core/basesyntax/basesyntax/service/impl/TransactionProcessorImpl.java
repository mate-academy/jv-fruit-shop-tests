package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.TransactionProcessor;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class TransactionProcessorImpl implements TransactionProcessor {
    private OperationStrategy operationStrategy;

    public TransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction transaction : fruitTransactions) {
            OperationHandler operationHandler = operationStrategy
                    .getHandler(transaction.getOperation());
            operationHandler.apply(transaction);
        }
    }
}
