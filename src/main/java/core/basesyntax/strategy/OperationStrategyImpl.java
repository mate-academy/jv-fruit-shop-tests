package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static final String INVALID_INPUT_PARAMETER
            = "Invalid input parameter in get()";
    private static final String MAP_INITIALIZED_EXCEPTION
            = "Map does not initialized completely! Put missed 'Operation' and 'OperationHandler'";
    private Map<Operation, OperationHandler> operationHandlersMap;

    public OperationStrategyImpl(Map<Operation, OperationHandler> operationHandlersMap) {
        this.operationHandlersMap = operationHandlersMap;
    }

    @Override
    public OperationHandler get(Operation operation) {
        if (operation == null) {
            throw new RuntimeException(INVALID_INPUT_PARAMETER);
        }
        if (operationHandlersMap.get(operation) == null) {
            throw new RuntimeException(MAP_INITIALIZED_EXCEPTION);
        }
        return operationHandlersMap.get(operation);
    }
}
