package core.basesyntax.operation;

import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<String, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<String, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler get(String operationType) {
        if (operationHandlerMap.get(operationType) == null) {
            throw new RuntimeException("Invalid operation type");
        }
        return operationHandlerMap.get(operationType);
    }
}
