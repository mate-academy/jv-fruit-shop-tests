package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;

public class IncreaseHandler implements ActionHandler {
    @Override
    public void getResultOfAction(String fruitName, int fruitCount) {
        Storage.fruits.put(fruitName, Storage.fruits.getOrDefault(fruitName, 0) + fruitCount);
    }
}
