package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void execute(Fruit fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("The negative number cannot be applied!");
        }
        if (fruit != null && Storage.stock.get(fruit) < quantity) {
            throw new RuntimeException("There is not enough fruit in stock!");
        }
        if (Storage.stock.containsKey(fruit)) {
            Storage.stock.put(fruit, Storage.stock.get(fruit) - quantity);
        }
    }
}
