package core.basesyntax.service;

import core.basesyntax.exception.UnknownOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class TransactionProcessor {
    private final Map<OperationType, OperationHandler> operationStrategy;

    public TransactionProcessor(Map<OperationType, OperationHandler> operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void applyOperation(FruitTransaction transaction) {
        OperationType operation = transaction.getOperation();
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        OperationHandler handler = operationStrategy.get(operation);
        if (handler == null) {
            throw new UnknownOperationException("Unknown operation type: " + operation);
        }
        handler.apply(Storage.inventory, fruit, quantity);
    }
}
