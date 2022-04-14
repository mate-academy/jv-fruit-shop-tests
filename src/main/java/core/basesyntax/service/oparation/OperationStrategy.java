package core.basesyntax.service.oparation;

import core.basesyntax.model.TypeOperations;

public interface OperationStrategy {
    OperationHandler get(TypeOperations typeOperations);
}
