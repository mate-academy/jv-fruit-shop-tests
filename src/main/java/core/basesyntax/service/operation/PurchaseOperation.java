package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void updateNumberOffFruit(String fruit, int amount) {
        if (fruit == null) {
            throw new RuntimeException("Null fruit exception!");
        }
        if (fruit.isEmpty()) {
            throw new RuntimeException("Fruit paramateres are empty!");
        }
        if (amount <= 0) {
            throw new RuntimeException("Amount can't be less than 1!");
        }
        if (!Character.isLetter(fruit.charAt(0))) {
            throw new RuntimeException("Fruit name should start from letters!");
        }
        if (Storage.storage.getOrDefault(fruit, 0)
                - amount < 0) {
            throw new RuntimeException("Fruit amount is not enough!");
        }
        Storage.storage.put(fruit, Storage.storage.getOrDefault(fruit, 0) - amount);
    }
}
