package core.basesyntax.strategy.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<String, OperationHandler> operations;

    public OperationStrategyImpl(Map<String, OperationHandler> operations) {
        this.operations = operations;
    }

    public OperationHandler getHandler(Transaction transaction) {
        String operationCode = transaction.getOperation().getCode();
        return operations.get(operationCode);
    }
}
