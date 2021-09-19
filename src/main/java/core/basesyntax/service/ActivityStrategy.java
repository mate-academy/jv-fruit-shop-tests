package core.basesyntax.service;

import core.basesyntax.service.activityhandler.ActivityHandler;

public interface ActivityStrategy {
    ActivityHandler get(String activityType);
}
