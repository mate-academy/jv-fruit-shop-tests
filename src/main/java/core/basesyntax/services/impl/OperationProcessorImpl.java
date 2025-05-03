package core.basesyntax.services.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.OperationProcessor;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class OperationProcessorImpl implements OperationProcessor {
    private OperationStrategy operationStrategy;

    public OperationProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction fruitTransaction : transactions) {
            operationStrategy.getHandler(fruitTransaction.getOperation())
                    .handle(fruitTransaction);
        }
    }
}
