package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {

        return operationHandlerMap.get(operation);
    }
}
