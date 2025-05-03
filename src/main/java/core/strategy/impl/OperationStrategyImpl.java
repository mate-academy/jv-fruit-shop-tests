package core.strategy.impl;

import core.strategy.Operation;
import core.strategy.OperationHandler;
import core.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<Operation, OperationHandler> operationsMap;

    public OperationStrategyImpl(Map<Operation, OperationHandler> operationsMap) {
        this.operationsMap = operationsMap;
    }

    @Override
    public OperationHandler get(Operation type) {
        return operationsMap.get(type);
    }
}
