package core.basesyntax.service;

import core.basesyntax.service.activities.ActivityHandler;
import core.basesyntax.service.activities.TypeOfActivities;

public interface ActivitiesStrategy {
    ActivityHandler get(TypeOfActivities type);
}
