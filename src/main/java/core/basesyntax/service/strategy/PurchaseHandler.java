package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.service.exceptions.ProductQuantityException;

public class PurchaseHandler implements ActivityHandler {
    private final Storage storage;

    public PurchaseHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void operate(String fruit, int quantity) {
        int currentQuantity = storage.getData().get(fruit);
        int newQuantity = currentQuantity - quantity;
        if (newQuantity <= 0) {
            throw new ProductQuantityException("Attempting to remove more "
                    + fruit + " than available.");
        }
        storage.getData().put(fruit, newQuantity);
    }
}
