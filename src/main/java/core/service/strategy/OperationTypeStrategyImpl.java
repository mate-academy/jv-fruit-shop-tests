package core.service.strategy;

import core.model.OperationType;
import core.service.operation.OperationTypeHandler;
import java.util.Map;

public class OperationTypeStrategyImpl implements OperationTypeStrategy {
    private Map<OperationType, OperationTypeHandler> handlerMap;

    public OperationTypeStrategyImpl(Map<OperationType, OperationTypeHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public OperationTypeHandler getHandle(OperationType operationType) {
        if (handlerMap.get(operationType) == null) {
            throw new RuntimeException("Invalid OperationType " + operationType);
        }
        return handlerMap.get(operationType);
    }
}
