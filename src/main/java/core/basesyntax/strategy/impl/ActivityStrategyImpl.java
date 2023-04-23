package core.basesyntax.strategy.impl;

import core.basesyntax.model.Activity;
import core.basesyntax.strategy.ActivityHandler;
import core.basesyntax.strategy.ActivityStrategy;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private final Map<Activity.Operation, ActivityHandler> activityHandlerMap;

    public ActivityStrategyImpl(Map<Activity.Operation, ActivityHandler> activityHandlerMap) {
        this.activityHandlerMap = activityHandlerMap;
    }

    @Override
    public ActivityHandler getHandler(Activity.Operation operation) {
        return activityHandlerMap.get(operation);
    }
}
