package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategy {

    private final Map<FruitTransaction.Operation, OperationHandler> handlers;

    public OperationStrategy(Map<FruitTransaction.Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    public OperationHandler getHandlers(FruitTransaction.Operation operation) {
        return handlers.get(operation);
    }

}
