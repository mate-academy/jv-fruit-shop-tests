package core.basesyntax.strategy;

import core.basesyntax.exceptions.NotSuchOperationException;
import core.basesyntax.services.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<String, OperationHandler> operationMap;

    public OperationStrategyImpl(Map<String, OperationHandler> operationMap) {
        this.operationMap = operationMap;
    }

    public OperationHandler getOperationHandler(String operation) {
        OperationHandler operationHandlerImpl = operationMap.get(operation);
        if (operationHandlerImpl == null) {
            throw new NotSuchOperationException("Operation " + operation + " is not valid.");
        }
        return operationHandlerImpl;
    }
}
