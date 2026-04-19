package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlersMap) {
        if (operationHandlersMap == null) {
            throw new RuntimeException("operationHandlersMap is null");
        }
        this.operationHandlersMap = operationHandlersMap;
    }

    @Override
    public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
        if (operationHandlersMap.get(operation) == null) {
            throw new RuntimeException("No operation handler for operation " + operation);
        }
        return operationHandlersMap.get(operation);

    }
}
