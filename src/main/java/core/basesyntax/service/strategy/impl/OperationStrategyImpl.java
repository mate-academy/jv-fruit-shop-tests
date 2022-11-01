package core.basesyntax.service.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationOperationsHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationOperationsHandlerMap) {
        this.operationOperationsHandlerMap = operationOperationsHandlerMap;
    }

    @Override
    public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
        return operationOperationsHandlerMap.get(operation);
    }
}
