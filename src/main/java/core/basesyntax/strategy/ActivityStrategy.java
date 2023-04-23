package core.basesyntax.strategy;

import core.basesyntax.model.Activity;

public interface ActivityStrategy {
    ActivityHandler getHandler(Activity.Operation operation);
}
