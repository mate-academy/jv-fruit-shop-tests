package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.inventory;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity for return cannot be negative");
        }
        inventory.put(fruit, inventory.getOrDefault(fruit, 0) + quantity);
    }
}
