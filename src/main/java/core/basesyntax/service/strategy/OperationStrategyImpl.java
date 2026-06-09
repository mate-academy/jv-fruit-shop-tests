package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap;

    public OperationStrategyImpl(
            Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap) {
        this.operationHandlersMap = operationHandlersMap;
    }

    @Override
    public OperationHandler operationHandler(FruitTransaction.Operation operationType) {

        if (operationHandlersMap == null) {
            throw new RuntimeException("input data is null");
        }

        if (operationHandlersMap.get(operationType) == null) {
            throw new RuntimeException("operation type don't exist");
        }

        return operationHandlersMap.get(operationType);
    }
}
