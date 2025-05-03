package core.basesyntax.impl.operation;

import core.basesyntax.impl.operation.operations.OperationHandler;
import core.basesyntax.model.Operation;

public interface OperationStrategy {
    OperationHandler getOperationHandler(Operation operation);
}
