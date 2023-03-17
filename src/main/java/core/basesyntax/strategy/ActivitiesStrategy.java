package core.basesyntax.strategy;

import core.basesyntax.strategy.activities.ActivitiesHandler;

public interface ActivitiesStrategy {
    ActivitiesHandler get(String firstChar);
}
