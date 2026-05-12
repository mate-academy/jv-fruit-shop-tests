package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class SupplyOperation implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        int fruitFromStorage = Storage.getInstance().getFruitStorage().getOrDefault(fruit, 0);
        Storage.getInstance().getFruitStorage().put(fruit, fruitFromStorage + quantity);
    }
}
