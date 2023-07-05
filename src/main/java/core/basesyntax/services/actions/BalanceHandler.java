package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;

public class BalanceHandler implements ActionHandler {
    @Override
    public Integer getResultOfAction(String fruitName, int fruitCount) {
        Storage.fruits.put(fruitName, fruitCount);
        return Storage.fruits.get(fruitName);
    }
}
