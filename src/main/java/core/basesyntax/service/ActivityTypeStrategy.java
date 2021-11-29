package core.basesyntax.service;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.activityhandler.ActivityHandler;

public interface ActivityTypeStrategy {
    ActivityHandler get(ActivityType activityType);
}
