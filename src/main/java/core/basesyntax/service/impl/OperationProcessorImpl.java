package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.OperationProcessor;
import java.util.List;

public class OperationProcessorImpl implements OperationProcessor {
    private OperationStrategy operationStrategy;

    public OperationProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processConvertedData(List<FruitTransaction> fruitTransactionList) {
        for (FruitTransaction fruitTransaction : fruitTransactionList) {
            operationStrategy.getOperationHandler(fruitTransaction).operate(fruitTransaction);
        }
    }
}
