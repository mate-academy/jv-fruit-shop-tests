package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.OperationHandlerProvider;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class OperationHandlerProviderImpl implements OperationHandlerProvider {
    private final Map<FruitTransaction.Operation, OperationHandler> handlers;

    public OperationHandlerProviderImpl(Map<FruitTransaction.Operation,
            OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        OperationHandler handler = handlers.get(operation);

        if (handler == null) {
            throw new RuntimeException("No handler found for operation: " + operation);
        }
        return handler;
    }
}
