package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandler;

    public OperationStrategyImpl(
            Map<FruitTransaction.Operation, OperationHandler> operationHandler) {
        this.operationHandler = operationHandler;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        return operationHandler.get(operation);
    }
}
