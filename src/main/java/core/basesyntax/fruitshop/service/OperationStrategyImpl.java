package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.model.OperationType;
import core.basesyntax.fruitshop.service.operations.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<OperationType, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<OperationType, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    public Map<OperationType, OperationHandler> getOperationHandlerMap() {
        return operationHandlerMap;
    }

    @Override
    public OperationHandler get(OperationType operation) {
        return operationHandlerMap.get(operation);
    }
}
