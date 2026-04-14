package mate.academy.fruitshop.service.impl;

import java.util.Map;
import mate.academy.fruitshop.model.FruitTransaction;
import mate.academy.fruitshop.service.OperationHandler;
import mate.academy.fruitshop.service.OperationStrategy;

public class OperationStrategyImpl implements OperationStrategy {

    private final Map<FruitTransaction.Operation, OperationHandler> handlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("Handlers map cannot be null or empty");
        }
        this.handlers = handlers;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation cannot be null");
        }

        OperationHandler handler = handlers.get(operation);

        if (handler == null) {
            throw new RuntimeException("No handler for operation: " + operation);
        }

        return handler;
    }
}
