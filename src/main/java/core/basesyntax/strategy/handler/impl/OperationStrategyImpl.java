package core.basesyntax.strategy.handler.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new NullPointerException("Operation cannot be null");
        }
        OperationHandler handler = operationHandlerMap.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found "
                    + "for operation: " + operation);
        }
        return handler;
    }
}
