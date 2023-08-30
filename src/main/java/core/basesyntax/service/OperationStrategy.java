package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;

public interface OperationStrategy {
    /**
     * Get Operation handler.
     * @param operation type of operation.
     * @return Handler fo certain operation.
     */
    OperationHandler getOperationHandler(FruitTransaction.Operation operation);
}
