package core.basesyntax.strategy.impl;

import core.basesyntax.model.ActivityType;
import core.basesyntax.strategy.ActivityHandler;
import core.basesyntax.strategy.ActivityStrategy;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private final Map<ActivityType, ActivityHandler> activityHandlerMap;

    public ActivityStrategyImpl(Map<ActivityType, ActivityHandler> activityHandlerMap) {
        this.activityHandlerMap = activityHandlerMap;
    }

    @Override
    public ActivityHandler getHandler(ActivityType activity) {
        return activityHandlerMap.get(activity);
    }
}
