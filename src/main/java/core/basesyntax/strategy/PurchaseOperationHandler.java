package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("The quantity must be positive");
        }
        int amountOfFruitAfterSell = Storage.getStorage().get(fruit) - quantity;
        if (amountOfFruitAfterSell < 0) {
            throw new RuntimeException("You can't sell more than you have");
        }
        Storage.getStorage().put(fruit, amountOfFruitAfterSell);
    }
}
