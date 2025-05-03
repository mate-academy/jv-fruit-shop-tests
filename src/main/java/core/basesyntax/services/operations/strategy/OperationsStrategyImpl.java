package core.basesyntax.services.operations.strategy;

import core.basesyntax.services.operations.Operation;
import java.util.Map;

public class OperationsStrategyImpl implements OperationsStrategy {
    private Map<String, Operation> strategyMap;

    public OperationsStrategyImpl(Map<String, Operation> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    public Operation getOperation(String operation) {
        return strategyMap.get(operation);
    }
}
