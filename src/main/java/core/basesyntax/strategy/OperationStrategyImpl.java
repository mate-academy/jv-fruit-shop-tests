package core.basesyntax.strategy;

import core.basesyntax.model.OperationType;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<OperationType, OperationHandler> operationStrategyMap;

    public OperationStrategyImpl(Map<OperationType, OperationHandler> operationStrategyMap) {
        this.operationStrategyMap = operationStrategyMap;
    }

    @Override
    public OperationHandler get(OperationType type) {
        if (type == null) {
            throw new RuntimeException("Operation strategy is null");
        }
        return operationStrategyMap.get(type);
    }
}
