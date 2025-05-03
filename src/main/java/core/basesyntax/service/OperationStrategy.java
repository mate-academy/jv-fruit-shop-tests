package core.basesyntax.service;

import core.basesyntax.operationtype.OperationHandler;

public interface OperationStrategy {
    OperationHandler getOperation(String operation);

}
