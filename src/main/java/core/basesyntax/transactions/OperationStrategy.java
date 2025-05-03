package core.basesyntax.transactions;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;

public class OperationStrategy {
    private static final String NULL_VALUE = "The value cannot be null";
    private static final String UNDER_ZERO_QUANTITY = "The quantity must be positive";
    private final Map<Operation, OperationHandler> handlers;

    public OperationStrategy(Map<Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    public OperationHandler handler(FruitTransaction fruitTransaction) {
        checkOnNull(fruitTransaction);
        OperationHandler operationHandler = handlers.get(fruitTransaction.getOperation());
        operationHandler.processTransaction(fruitTransaction);
        return operationHandler;
    }

    private void checkOnNull(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null
                || fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null) {
            throw new IllegalArgumentException(NULL_VALUE + fruitTransaction);
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new IllegalArgumentException(UNDER_ZERO_QUANTITY + fruitTransaction);
        }

    }
}
