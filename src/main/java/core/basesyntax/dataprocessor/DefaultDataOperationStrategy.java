package core.basesyntax.dataprocessor;

import java.util.Map;

public class DefaultDataOperationStrategy {
    private final Map<Operation, OperationHandler> handlers;

    public DefaultDataOperationStrategy(Map<Operation, OperationHandler> handlers) {
        if (handlers == null) {
            throw new IllegalArgumentException("Handlers map cannot be null");
        }
        this.handlers = handlers;
    }

    public OperationHandler getHandler(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Invalid operation provided");
        }
        OperationHandler handler = handlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("Handler not found for operation: " + operation);
        }
        return handler;
    }
}
