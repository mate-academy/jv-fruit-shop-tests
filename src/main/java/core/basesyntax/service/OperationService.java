package core.basesyntax.service;

import core.basesyntax.model.OperationWithFruit;

public interface OperationService {
    core.basesyntax.strategy.Operation getOperationHandler(OperationWithFruit operation);
}
