package basesyntax.service.strategy;

import basesyntax.service.strategy.handlers.OperationHandler;

public interface OperationStrategy {
    OperationHandler get(String operationType);
}
