package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Activity;
import core.basesyntax.strategy.ActivityHandler;

public class PurchaseHandlerImpl implements ActivityHandler {
    @Override
    public void process(Activity activity) {
        if (Storage.storeItems.get(activity.getItem()) < activity.getQuantity()) {
            throw new RuntimeException("Can't process purchase activity, not enough items");
        } else {
            Storage.storeItems.merge(activity.getItem(), activity.getQuantity(), (a, b) -> a - b);
        }
    }
}
