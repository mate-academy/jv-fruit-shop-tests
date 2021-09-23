package core.basesyntax.operations;

import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<String, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<String, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperation(String typeOperation) {
        if (operationHandlerMap.containsKey(typeOperation)) {
            return operationHandlerMap.get(typeOperation);
        }
        throw new RuntimeException("Invalid operation");
    }
}
