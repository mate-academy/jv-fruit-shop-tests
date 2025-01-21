package core.basesyntax.service;

import core.basesyntax.exception.UnknownOperationException;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class TransactionProcessor {
    public static void applyOperation(OperationType operation, String fruit, int quantity,
                                      Map<OperationType, OperationHandler> operationStrategy) {
        OperationHandler handler = operationStrategy.get(operation);
        if (handler == null) {
            throw new UnknownOperationException("Unknown operation type: " + operation);
        }
        handler.apply(Storage.inventory, fruit, quantity);
    }
}
