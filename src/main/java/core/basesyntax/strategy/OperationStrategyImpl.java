package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operationOperationHandlerMap;

    public OperationStrategyImpl(
            Map<Operation, OperationHandler> operationOperationHandlerMap) {
        this.operationOperationHandlerMap = operationOperationHandlerMap;
    }

    @Override
    public OperationHandler get(Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Unknown operation!");
        }
        return operationOperationHandlerMap.get(operation);
    }
}
