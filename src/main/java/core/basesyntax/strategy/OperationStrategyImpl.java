package core.basesyntax.strategy;

import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation is null");
        }
        if (!operationHandlers.containsKey(operation)) {
            return null;
        }
        return operationHandlers.get(operation);
    }
}
