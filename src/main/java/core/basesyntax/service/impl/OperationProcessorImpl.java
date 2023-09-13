package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationProcessor;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class OperationProcessorImpl implements OperationProcessor {
    private OperationStrategy operationStrategy;

    public OperationProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void applyOperation(List<FruitTransaction> fruitTransactionList) {
        fruitTransactionList.forEach(f -> operationStrategy.get(f.getOperation()).process(f));
    }
}
