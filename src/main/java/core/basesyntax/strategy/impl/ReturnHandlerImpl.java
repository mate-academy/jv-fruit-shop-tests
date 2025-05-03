package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Activity;
import core.basesyntax.strategy.ActivityHandler;

public class ReturnHandlerImpl implements ActivityHandler {
    @Override
    public void process(Activity activity) {
        Storage.storeItems.merge(activity.getItem(), activity.getQuantity(), Integer::sum);
    }
}
