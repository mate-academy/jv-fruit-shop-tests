package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public void execute(Fruit fruit, Integer quantity) {
        if (quantity != null && quantity < 0) {
            throw new RuntimeException("The negative number cannot be applied!");
        }
        if (quantity != null && Storage.stock.containsKey(fruit)) {
            Storage.stock.put(fruit, Storage.stock.get(fruit) + quantity);
        }
    }
}
