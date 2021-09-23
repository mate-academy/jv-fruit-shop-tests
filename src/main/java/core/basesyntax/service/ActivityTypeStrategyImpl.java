package core.basesyntax.service;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.activityhandler.ActivityHandler;
import java.util.Map;

public class ActivityTypeStrategyImpl implements ActivityTypeStrategy {
    private static final String notSupportedActivityType
            = "Given activity type is not supported";
    private Map<ActivityType, ActivityHandler> activityTypesHandlerMap;

    public ActivityTypeStrategyImpl(Map<ActivityType, ActivityHandler>
                                            activityTypesHandlerMap) {
        this.activityTypesHandlerMap = activityTypesHandlerMap;
    }

    @Override
    public ActivityHandler get(ActivityType activityType) {
        ActivityHandler activityHandler;
        if ((activityHandler = activityTypesHandlerMap.get(activityType)) == null) {
            throw new IllegalArgumentException(notSupportedActivityType);
        }
        return activityHandler;
    }
}
