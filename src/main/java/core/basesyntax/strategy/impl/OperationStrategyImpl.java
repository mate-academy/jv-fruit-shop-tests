package core.basesyntax.strategy.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static final String MAP_IS_NULL = "Map with handlers is NULL!!!";
    private final Map<Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperationHandler(Operation operation) {
        if (operationHandlerMap == null) {
            throw new RuntimeException(MAP_IS_NULL);
        }
        return operationHandlerMap.get(operation);
    }
}
