package strategy;

import db.Storage;

public class ReturnImpl implements Operation {

    private static final String QUANTITY_NOT_NEGATIVE = "Purchase quantity cannot be negative: ";
    private static final int ZERO = 0;

    @Override
    public void execute(String fruit, int quantity) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }

        if (quantity < ZERO) {
            throw new IllegalArgumentException(QUANTITY_NOT_NEGATIVE + quantity);
        } else if (quantity == ZERO) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        int currentQuantity = Storage.storage.getOrDefault(fruit, ZERO);
        Storage.storage.put(fruit, currentQuantity + quantity);
    }
}
