package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void handle(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("You can't supply negative quantity");
        }
        int newValueAfterSupplying = Storage.getStorage().get(fruit) + quantity;
        Storage.getStorage().put(fruit, newValueAfterSupplying);
    }
}
