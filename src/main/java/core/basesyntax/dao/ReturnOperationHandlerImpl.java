package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class ReturnOperationHandlerImpl implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (!Storage.fruitStorage.containsKey(fruit)) {
            Storage.fruitStorage.put(fruit, quantity);
        }
        int currentQuantity = Storage.fruitStorage.get(fruit);
        Storage.fruitStorage.put(fruit, currentQuantity + quantity);
    }
}
