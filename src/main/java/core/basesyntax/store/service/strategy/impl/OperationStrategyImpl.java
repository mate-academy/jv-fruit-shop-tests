package core.basesyntax.store.service.strategy.impl;

import core.basesyntax.store.handler.OperationHandler;
import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.service.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation,OperationHandler> handlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction.Operation operation) {
        try {
            OperationHandler handler = handlers.get(operation);
            if (handler == null) {
                throw new IllegalArgumentException("No handler found for operation: " + operation);
            }
            return handler;
        } catch (IllegalArgumentException e) {
            System.err.println("Warning: " + e.getMessage());
            return operationData -> {
                System.out.println("Skipping operation: " + operation);
            };
        }
    }
}
