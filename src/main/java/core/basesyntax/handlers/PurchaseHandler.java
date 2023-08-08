package core.basesyntax.handlers;

import core.basesyntax.db.Storage;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        Integer currentQuantity = Storage.storage.getOrDefault(fruit, 0);
        int newQuantity = currentQuantity - quantity;
        if (newQuantity < 0) {
            throw new RuntimeException("The quantity of fruits can't be less than 0");
        }
        Storage.storage.put(fruit, newQuantity);
    }
}
