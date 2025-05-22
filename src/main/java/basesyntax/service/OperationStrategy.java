package basesyntax.service;

import basesyntax.model.Operation;
import basesyntax.service.handler.OperationHandler;

public interface OperationStrategy {
    OperationHandler getHandler(Operation operation);
}
