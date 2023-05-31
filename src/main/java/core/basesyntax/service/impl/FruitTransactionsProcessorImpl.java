package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionsProcessor;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class FruitTransactionsProcessorImpl implements FruitTransactionsProcessor {
    private OperationStrategy operationStrategy;

    public FruitTransactionsProcessorImpl(OperationStrategy operationStrategy) {
        if (operationStrategy == null) {
            throw new IllegalArgumentException("Strategy can't be null");
        }
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new RuntimeException("Transactions can't be null.");
        }
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handle(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        }
    }
}
