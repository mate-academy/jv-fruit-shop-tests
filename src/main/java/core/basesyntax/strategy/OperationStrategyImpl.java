package core.basesyntax.strategy;

import core.basesyntax.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
        OperationHandler operationHandler = operationHandlerMap.get(operation);
        if (operationHandler == null) {
            throw new RuntimeException("Operation doesn't exist by number : " + operation);
        }
        return operationHandler;
    }
}
