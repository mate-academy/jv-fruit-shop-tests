package core.basesyntax.strategy.impl;

import core.basesyntax.model.ShopTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.Map;
import java.util.Objects;

public class ShopOperationStrategy implements OperationStrategy {
    private final Map<ShopTransaction.Operation, ShopOperationHandler> handlers;

    public ShopOperationStrategy(Map<ShopTransaction.Operation, ShopOperationHandler> handlers) {
        this.handlers = Objects.requireNonNull(handlers, "handlers is null");
    }

    @Override
    public ShopOperationHandler resolve(ShopTransaction.Operation operationType) {
        Objects.requireNonNull(operationType, "Operation is null");
        ShopOperationHandler handler = handlers.get(operationType);

        if (handler == null) {
            throw new RuntimeException("No handler for operation: " + operationType);
        }

        return handler;
    }
}
