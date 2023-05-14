package core.basesyntax.service;

import core.basesyntax.model.Activity;

public interface ActivityService {
    Activity parse(String activityInfo);
}
