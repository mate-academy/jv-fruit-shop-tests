package core.basesyntax.service;

import core.basesyntax.service.activityhandler.ActivityHandler;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private final Map<String, ActivityHandler> activityHandlerMap;

    public ActivityStrategyImpl(Map<String, ActivityHandler> activityHandlerMap) {
        this.activityHandlerMap = activityHandlerMap;
    }

    @Override
    public ActivityHandler get(String activityType) {

        if (activityHandlerMap.containsKey(activityType)) {
            return activityHandlerMap.get(activityType);
        } else {
            throw new RuntimeException("Wrong activity type in the file");
        }
    }
}
