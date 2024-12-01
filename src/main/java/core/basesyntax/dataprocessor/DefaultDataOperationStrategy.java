package core.basesyntax.dataprocessor;

import java.util.Map;

public class DefaultDataOperationStrategy {
    private final Map<Operation, OperationHandler> handlers;

    public DefaultDataOperationStrategy(Map<Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    public OperationHandler getHandler(Operation operation) {
        OperationHandler handler = handlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for operation: " + operation);
        }
        return handler;
    }
}
