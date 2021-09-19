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

        if (activityType.equals("b") || activityType.equals("p")
                || activityType.equals("r") || activityType.equals("s")) {
            return activityHandlerMap.get(activityType);
        }

        throw new RuntimeException("Wrong activity type in the file");
    }
}
