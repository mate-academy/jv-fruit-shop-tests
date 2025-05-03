package core.basesyntax.basesyntax.service.strategy;

import core.basesyntax.basesyntax.model.Operations;

public interface OperationHandler {
    Operations getOperation();

    int applyOperation(int result, int quantity);
}
