package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler process(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation can not be null");
        }
        return operationHandlerMap.get(operation);
    }
}
