package core.basesyntax.service.strategy;

import core.basesyntax.model.Operation;

public interface OperationStrategy {
    OperationHandler processOperation(Operation operation);
}
