package core.basesyntax.basesyntax.service.strategy;

import core.basesyntax.basesyntax.model.Operations;

public interface OperationsStrategy {
    OperationHandler get(Operations operation);
}
