package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategyProvider {
    private final Map<FruitTransaction.OperationType, OperationHandler> handlers;

    public OperationStrategyProvider(Map<FruitTransaction.OperationType,
            OperationHandler> handlers) {
        this.handlers = handlers;
    }

    public OperationHandler getHandler(FruitTransaction.OperationType operation) {
        OperationHandler handler = handlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("Unknown operation: " + operation);
        }
        return handler;
    }
}
