package strategy.impl;

import java.util.Map;
import model.ActivityType;
import strategy.ActivityTypeHandler;
import strategy.ActivityTypeStrategy;

public class ActivityTypeStrategyImpl implements ActivityTypeStrategy {
    private final Map<ActivityType, ActivityTypeHandler> activityTypeHandlerMap;

    public ActivityTypeStrategyImpl(Map<ActivityType, ActivityTypeHandler> activityTypeHandlerMap) {
        this.activityTypeHandlerMap = activityTypeHandlerMap;
    }

    @Override
    public ActivityTypeHandler get(ActivityType type) {
        if (!activityTypeHandlerMap.containsKey(type)) {
            throw new RuntimeException("There is no such type!");
        }
        return activityTypeHandlerMap.get(type);
    }
}
