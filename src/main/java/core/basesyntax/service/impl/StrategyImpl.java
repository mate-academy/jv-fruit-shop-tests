package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.Strategy;
import java.util.Map;

public class StrategyImpl implements Strategy {
    private final Map<String, OperationHandler> stringStoreActivitiesMap;

    public StrategyImpl(Map<String, OperationHandler> stringStoreActivitiesMap) {
        this.stringStoreActivitiesMap = stringStoreActivitiesMap;
    }

    @Override
    public OperationHandler getActivity(String storeActivityType) {
        if (!stringStoreActivitiesMap.containsKey(storeActivityType)) {
            throw new RuntimeException("No such activityType");
        }
        return stringStoreActivitiesMap.get(storeActivityType);
    }
}
