package core.basesyntax.strategy;

import core.basesyntax.service.operation.OperationHandler;

public interface OperationStrategy {
    OperationHandler get(String operation);
}
