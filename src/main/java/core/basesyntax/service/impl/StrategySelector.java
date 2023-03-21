package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;
import java.util.Map;

public class StrategySelector {
    private static final String FAILED_TO_CHOOSE_STRATEGY
            = "Failed to choose strategy for operation ";
    private final Map<Operation, SaveStrategy> strategies;

    public StrategySelector(Map<Operation, SaveStrategy> strategies) {
        this.strategies = strategies;
    }

    public SaveStrategy selectStrategy(Operation operation) {
        if (!strategies.containsKey(operation)) {
            throw new RuntimeException(
                    FAILED_TO_CHOOSE_STRATEGY + operation.name());
        }
        return strategies.get(operation);
    }
}
