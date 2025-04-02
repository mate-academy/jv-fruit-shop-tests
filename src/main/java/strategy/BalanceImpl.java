package strategy;

import db.Storage;

public class BalanceImpl implements Operation {

    private static final int ZERO = 0;

    @Override
    public void execute(String fruit, int quantity) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit cannot be null");
        }
        Storage.storage.put(fruit, quantity);
        if (quantity < ZERO) {
            throw new IllegalArgumentException("quantity cannot be zero or negative");
        } else if (quantity == ZERO) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
    }
}
