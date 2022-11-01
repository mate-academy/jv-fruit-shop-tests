package core.basesyntax.strategy.operation;

import core.basesyntax.db.Store;
import core.basesyntax.exception.NotEnoughFruitQuantityException;

public class PurchaseOperationImpl implements OperationHandler {
    @Override
    public void getResultBalance(String fruitName, int value) {
        if (Store.FRUIT_STORAGE.get(fruitName) < value) {
            throw new NotEnoughFruitQuantityException("Not enough fruit quantity in storage");
        }
        int oldVale = Store.FRUIT_STORAGE.get(fruitName);
        int newValue = oldVale - value;
        Store.FRUIT_STORAGE.put(fruitName, newValue);
    }
}
