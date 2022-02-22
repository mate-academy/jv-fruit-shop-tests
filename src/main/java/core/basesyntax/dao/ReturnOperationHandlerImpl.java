package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class ReturnOperationHandlerImpl implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (fruit == null || quantity < 0) {
            throw new RuntimeException("Invalid input parameters.");
        }
        if (!Storage.fruitStorage.containsKey(fruit)) {
            Storage.fruitStorage.put(fruit, quantity);
            return;
        }
        int currentQuantity = Storage.fruitStorage.get(fruit);
        Storage.fruitStorage.put(fruit, currentQuantity + quantity);
    }
}
