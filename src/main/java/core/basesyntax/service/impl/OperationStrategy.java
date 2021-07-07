package core.basesyntax.service.impl;

import core.basesyntax.service.Strategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class OperationStrategy implements Strategy {
    private Map<String, OperationHandler> handlers;

    public OperationStrategy(Map<String, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler get(String line) {
        if (line == null || line.length() != 1) {
            return null;
        }
        return handlers.get(line);
    }
}
