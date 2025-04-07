package strategy;

import db.Storage;

public class PurchaseImpl implements Operation {
    private static final int ZERO = 0;

    @Override
    public void execute(String fruit, int quantity) {
        if (quantity < ZERO) {
            throw new IllegalArgumentException("Purchase quantity cannot be negative: " + quantity);
        } else if (quantity == ZERO) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        int currentQuantity = Storage.storage.getOrDefault(fruit, ZERO);
        int newQuantity = currentQuantity - quantity;
        if (newQuantity < ZERO) {
            throw new IllegalArgumentException("Cannot purchase more fruits than available "
                    + "for fruit: " + fruit + ". Available: " + fruit);
        }
        Storage.storage.put(fruit, newQuantity);
    }
}
