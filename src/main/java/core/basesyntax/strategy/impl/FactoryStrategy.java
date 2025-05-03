package core.basesyntax.strategy.impl;

import core.basesyntax.exception.FruitException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class FactoryStrategy {
    private final Map<FruitTransaction.Operation, OperationStrategy> strategies;

    public FactoryStrategy(Map<FruitTransaction.Operation, OperationStrategy> strategies) {
        checkStrategies(strategies);
        this.strategies = strategies;
    }

    public OperationStrategy getOperationStrategy(FruitTransaction.Operation operation) {
        return strategies.get(operation);
    }

    private void checkStrategies(Map<FruitTransaction.Operation, OperationStrategy> strategies) {
        StringBuilder noStrategiesForOperations = new StringBuilder();
        for (FruitTransaction.Operation value : FruitTransaction.Operation.values()) {
            if (!strategies.containsKey(value)) {
                noStrategiesForOperations
                        .append(noStrategiesForOperations.length() == 0 ? "" : ", ")
                        .append(value.name());
            }
        }
        if (noStrategiesForOperations.length() > 0) {
            throw new FruitException("There is no strategy for the operation "
                    + noStrategiesForOperations);
        }
    }
}
