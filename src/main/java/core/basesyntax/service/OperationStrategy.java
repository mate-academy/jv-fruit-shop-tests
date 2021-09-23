package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.Handler;

public interface OperationStrategy {
    Handler getHandler(Operation operation);
}
