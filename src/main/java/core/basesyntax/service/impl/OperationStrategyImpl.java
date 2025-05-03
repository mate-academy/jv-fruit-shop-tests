package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlers) {
        if (!operationHandlers.isEmpty()) {
            this.operationHandlers = operationHandlers;
        } else {
            throw new RuntimeException("HashMap is empty...");
        }
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation == null || !operation.equals(FruitTransaction.Operation.BALANCE)
                && !operation.equals(FruitTransaction.Operation.SUPPLY)
                && !operation.equals(FruitTransaction.Operation.PURCHASE)
                && !operation.equals(FruitTransaction.Operation.RETURN)) {
            return null;
        }
        return operationHandlers.get(operation);
    }
}
