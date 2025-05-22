package basesyntax.service;

import basesyntax.model.Operation;
import basesyntax.service.handler.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> handlers;

    public OperationStrategyImpl(Map<Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler getHandler(Operation operation) {
        return handlers.get(operation);
    }
}
