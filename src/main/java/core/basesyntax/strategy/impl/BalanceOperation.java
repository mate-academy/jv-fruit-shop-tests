package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperation implements OperationHandler {
    @Override
    public void execute(Fruit fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("The negative number cannot be applied!");
        }
        if (fruit == null) {
            throw new NullPointerException("The storage cannot store null objects");
        }
        Storage.stock.put(fruit, quantity);
    }
}
