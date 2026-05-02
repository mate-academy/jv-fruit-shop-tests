package core.basesyntax.basesyntax.strategy;

import core.basesyntax.basesyntax.db.StorageImpl;

public class SupplyOperation implements OperationHandler {
    private static final int ZERO_QUANTITY = 0;

    @Override
    public void apply(String fruit, int quantity, StorageImpl storage) {
        if (quantity < ZERO_QUANTITY) {
            throw new RuntimeException("Quantity cannot be negative: " + quantity);
        }
        int current = storage.getQuantity(fruit);
        storage.setQuantity(fruit, current + quantity);
    }
}
