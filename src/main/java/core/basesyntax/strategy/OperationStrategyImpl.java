package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.OperationType, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.OperationType,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperationStrategy(FruitTransaction.OperationType type) {
        return operationHandlerMap.get(type);
    }
}
