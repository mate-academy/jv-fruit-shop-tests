package core.basesyntax.strategy.operation;

import core.basesyntax.db.Store;

public class PurchaseOperationImpl implements OperationHandler {
    @Override
    public void getResultBalance(String fruitName, int value) {
        if (Store.FRUIT_STORAGE.get(fruitName) == null) {
            throw new RuntimeException("There are no " + fruitName + "in storage");
        }
        int oldVale = Store.FRUIT_STORAGE.get(fruitName);
        int newValue = oldVale - value;
        Store.FRUIT_STORAGE.put(fruitName, newValue);
    }
}
