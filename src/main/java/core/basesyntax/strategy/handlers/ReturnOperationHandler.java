package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity can't be negative");
        }
        if (Storage.get(fruit) == null) {
            Storage.put(fruit, quantity);
            return;
        }
        Storage.put(fruit, Storage.get(fruit) + quantity);
    }
}
