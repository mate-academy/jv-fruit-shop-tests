package core.basesyntax.strategy.impl;

import core.basesyntax.handler.impl.OperationHandler;

public interface OperationStrategy {
    OperationHandler get(String operation);
}
