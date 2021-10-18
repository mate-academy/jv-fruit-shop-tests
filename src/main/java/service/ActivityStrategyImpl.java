package service;

import java.util.Map;
import service.activity.ActivityHandler;

public class ActivityStrategyImpl implements ActivityStrategy {
    private Map<String, ActivityHandler> activityHandlersMap;

    public ActivityStrategyImpl(Map<String, ActivityHandler> activityHandlersMap) {
        this.activityHandlersMap = activityHandlersMap;
    }

    @Override
    public ActivityHandler select(String activityType) {
        ActivityHandler activityHandler = activityHandlersMap.get(activityType);
        if (activityHandler == null) {
            throw new RuntimeException("Incorrect activity: " + activityType);
        }
        return activityHandler;
    }
}
