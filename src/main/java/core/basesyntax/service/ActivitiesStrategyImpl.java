package core.basesyntax.service;

import core.basesyntax.service.activities.ActivityHandler;
import core.basesyntax.service.activities.TypeOfActivities;
import java.util.Map;

public class ActivitiesStrategyImpl implements ActivitiesStrategy {
    private Map<TypeOfActivities, ActivityHandler> activitiesMap;

    public ActivitiesStrategyImpl(Map<TypeOfActivities, ActivityHandler> activitiesMap) {
        this.activitiesMap = activitiesMap;
    }

    @Override
    public ActivityHandler get(TypeOfActivities type) {
        if (activitiesMap.containsKey(type)) {
            return activitiesMap.get(type);
        }
        throw new RuntimeException("Can't find type of activities, " + type);
    }
}
