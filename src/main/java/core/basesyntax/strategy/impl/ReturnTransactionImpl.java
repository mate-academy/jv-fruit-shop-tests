package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class ReturnTransactionImpl implements OperationHandler {
    private static final int MINIMUM_STORAGE_QUANTITY = 0;

    @Override
    public void handle(String fruit, int quantity) {
        if (fruit == null) {
            throw new RuntimeException("Return operation failed! Incoming fruit can't be null!");
        }
        Map<String, Integer> storageMap = Storage.getFruitStore();
        if (storageMap.get(fruit) == null) {
            throw new RuntimeException("Return operation failed! Incoming fruit out of Storage!"
                    + " Please use Balance operation first!");
        }
        int currentStoredFruit = storageMap.get(fruit);
        if (MINIMUM_STORAGE_QUANTITY > currentStoredFruit || MINIMUM_STORAGE_QUANTITY > quantity) {
            throw new RuntimeException("Return value can't be less than "
                    + MINIMUM_STORAGE_QUANTITY);
        }
        storageMap.put(fruit, currentStoredFruit + quantity);
        Storage.setFruitStore(storageMap);
    }
}
