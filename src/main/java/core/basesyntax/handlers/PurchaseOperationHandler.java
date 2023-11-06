package core.basesyntax.handlers;

import core.basesyntax.db.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void operate(String fruit, int quantity) {
        int quantityAfterPurchase = Storage.fruits.get(fruit) - quantity;
        checkNewQuantity(quantityAfterPurchase);
        Storage.fruits.put(fruit,quantityAfterPurchase);
    }

    private void checkNewQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new RuntimeException("Quantity cannot be negative" + newQuantity);
        }
    }
}
