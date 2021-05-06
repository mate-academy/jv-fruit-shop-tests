package core.basesyntax.service.impl;

import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.Operation;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<String, Operation> activityOperationMap;

    public OperationStrategyImpl(Map<String, Operation> activityOperationMap) {
        this.activityOperationMap = activityOperationMap;
    }

    @Override
    public Operation get(String activity) {
        return activityOperationMap.get(activity);
    }
}
