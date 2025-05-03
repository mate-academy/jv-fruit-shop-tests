package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategy(Map<FruitTransaction.Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    public OperationHandler get(FruitTransaction.Operation operation) {
        OperationHandler handler = operationHandlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
        return handler;
    }
}
