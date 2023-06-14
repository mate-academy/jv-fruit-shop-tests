package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> strategyOperations;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> strategyOperations) {
        this.strategyOperations = strategyOperations;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        return strategyOperations.get(operation);
    }
}
