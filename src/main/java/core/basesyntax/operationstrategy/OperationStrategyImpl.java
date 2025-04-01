package core.basesyntax.operationstrategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operationservice.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlers) {
        if (operationHandlers == null || operationHandlers.isEmpty()) {
            throw new IllegalArgumentException("Operation handlers map cannot be null or empty");
        }
        this.operationHandlers = Map.copyOf(operationHandlers);
    }

    @Override
    public OperationHandler getHandler(FruitTransaction.Operation operation) {
        OperationHandler handler = operationHandlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for operation: " + operation);
        }
        return handler;
    }
}
