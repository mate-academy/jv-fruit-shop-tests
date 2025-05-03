package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class PurchaseOperationHandlerImpl implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (fruit == null || quantity < 0) {
            throw new RuntimeException("Invalid input parameters.");
        }
        if (!Storage.fruitStorage.containsKey(fruit)) {
            throw new RuntimeException("There are no " + fruit + " in storage now.");
        }
        int currentQuantity = Storage.fruitStorage.get(fruit);
        if (currentQuantity < quantity) {
            throw new RuntimeException("You can't buy "
                    + quantity + " "
                    + fruit
                    + " because there are only "
                    + currentQuantity
                    + " in the storage");
        }
        int newQuantity = currentQuantity - quantity;
        Storage.fruitStorage.put(fruit, newQuantity);
    }
}
