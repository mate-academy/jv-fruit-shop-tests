package core.basesyntax.service.impl;

import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;

public class FruitServiceImpl implements FruitService {
    private final OperationStrategy operationStrategy;

    public FruitServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(String inputFilePath) {
        // Implement this method
    }
}
