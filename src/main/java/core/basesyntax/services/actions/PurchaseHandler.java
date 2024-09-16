package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;

public class PurchaseHandler implements ActionHandler {
    @Override
    public Integer getResultOfAction(String fruitName, int fruitCount) {
        if (Storage.fruits.get(fruitName) - fruitCount < 0) {
            throw new RuntimeException("Storage don't have this quantity " + fruitCount);
        }

        Storage.fruits.put(
                fruitName, Storage.fruits.getOrDefault(
                        fruitName, 0) - fruitCount);
        return Storage.fruits.get(fruitName);
    }
}
