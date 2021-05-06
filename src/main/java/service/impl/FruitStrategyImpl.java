package service.impl;

import model.OperationType;
import service.FruitStrategy;
import service.OperationHandler;

import java.util.Map;

public class FruitStrategyImpl implements FruitStrategy {
    private Map<OperationType, OperationHandler> activityHandleMap;

    public FruitStrategyImpl(Map<OperationType, OperationHandler> activityHandleMap) {
        this.activityHandleMap = activityHandleMap;
    }

    @Override
    public OperationHandler get(OperationType type) {
        return activityHandleMap.get(type);
    }
}
