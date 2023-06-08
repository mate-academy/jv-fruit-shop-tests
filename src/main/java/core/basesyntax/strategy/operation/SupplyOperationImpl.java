package core.basesyntax.strategy.operation;

import core.basesyntax.db.Store;

public class SupplyOperationImpl implements OperationHandler {
    @Override
    public void getResultBalance(String fruitName, int value) {
        if (Store.FRUIT_STORAGE.get(fruitName) == null) {
            Store.FRUIT_STORAGE.put(fruitName, value);
        } else {
            int oldVale = Store.FRUIT_STORAGE.get(fruitName);
            int newValue = oldVale + value;
            Store.FRUIT_STORAGE.put(fruitName, newValue);
        }
    }
}
