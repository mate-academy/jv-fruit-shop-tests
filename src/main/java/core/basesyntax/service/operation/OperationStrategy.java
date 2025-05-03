package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategy(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation.equals(null)) {
            throw new RuntimeException("Value cannot be null");
        }
        if (!operationHandlerMap.containsKey(operation)) {
            throw new RuntimeException("No action found for this operation");
        }
        return operationHandlerMap.get(operation);
    }
}

