package core.basesyntax.service.strategy.impl;

import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(
            Map<Operation, OperationHandler> operationHandlerMapHandlerMap) {
        this.operationHandlerMap = operationHandlerMapHandlerMap;
    }

    @Override
    public OperationHandler getOperationHandler(Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation can't be null");
        }
        return operationHandlerMap.get(operation);
    }
}
