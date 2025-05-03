package core.basesyntax.shop.service;

import core.basesyntax.shop.service.operations.OperationHandler;
import java.util.Map;

public class OperationsStrategyImpl implements OperationsStrategy {
    private Map<String, OperationHandler> operationMap;

    public OperationsStrategyImpl(Map<String, OperationHandler> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public OperationHandler get(String operation) {
        return operationMap.get(operation);
    }
}
