package core.basesyntax.strategy;

import core.basesyntax.strategy.activity.ActivityHandler;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private final Map<String, ActivityHandler> activityHandlerMap;

    public ActivityStrategyImpl(Map<String, ActivityHandler> activityHandlerMap) {
        this.activityHandlerMap = activityHandlerMap;
    }

    @Override
    public ActivityHandler get(String activityType) {
        return activityHandlerMap.get(activityType);
    }
}
