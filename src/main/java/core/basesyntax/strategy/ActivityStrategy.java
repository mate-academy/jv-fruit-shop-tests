package core.basesyntax.strategy;

import core.basesyntax.model.ActivityType;

public interface ActivityStrategy {
    ActivityHandler getHandler(ActivityType activity);
}
