package core.basesyntax.servise;

import core.basesyntax.servise.activity.ActivityHandler;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private final Map<String, ActivityHandler> activities;

    public ActivityStrategyImpl(Map<String, ActivityHandler> activities) {
        this.activities = activities;
    }

    @Override
    public ActivityHandler getActivity(String activity) {
        if (activities.get(activity) == null) {
            throw new RuntimeException("Unknown Activity");
        }
        return activities.get(activity);
    }
}
