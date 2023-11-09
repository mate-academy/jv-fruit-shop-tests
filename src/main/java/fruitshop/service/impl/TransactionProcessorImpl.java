package fruitshop.service.impl;

import fruitshop.model.FruitTransaction;
import fruitshop.operation.OperationStrategy;
import fruitshop.service.TransactionProcessor;

public class TransactionProcessorImpl implements TransactionProcessor {
    private final OperationStrategy operationStrategy;

    public TransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void process(FruitTransaction fruitTransaction) {
        operationStrategy.get(fruitTransaction.getOperation()).accept(fruitTransaction);
    }
}
