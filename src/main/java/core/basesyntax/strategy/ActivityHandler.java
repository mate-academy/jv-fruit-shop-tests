package core.basesyntax.strategy;

import core.basesyntax.model.Activity;

public interface ActivityHandler {
    void process(Activity activity);
}
