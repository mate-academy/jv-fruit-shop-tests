package core.basesyntax.strategy.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<Operation, OperationHandler> handlerMap) {
        this.operationHandlerMap = handlerMap;
    }

    @Override
    public OperationHandler get(Operation operation) {
        return operationHandlerMap.get(operation);
    }
}
