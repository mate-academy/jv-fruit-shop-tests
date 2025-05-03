package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.Strategy;
import java.util.HashMap;
import java.util.Map;

public class StrategyImpl implements Strategy {
    private final Map<String, OperationHandler> strategyMap = new HashMap<>();

    @Override
    public void addStrategyType(String operationType, OperationHandler operationHandler) {
        strategyMap.put(operationType, operationHandler);
    }

    @Override
    public OperationHandler getStrategyType(String type) {
        return strategyMap.get(type);
    }
}
