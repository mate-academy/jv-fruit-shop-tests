package core.basesyntax.strategy.impl.impl;

import core.basesyntax.handler.impl.OperationHandler;
import core.basesyntax.strategy.impl.OperationStrategy;

import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<String, OperationHandler> handlers;

    public OperationStrategyImpl(Map<String, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler get(String operation) {
        return handlers.get(operation);
    }
}
