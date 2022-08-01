package core.basesyntax.strategy;

import core.basesyntax.strategy.handlers.OperationHandler;

public interface OperationHandlerStrategy {
    OperationHandler get(String operation);
}
