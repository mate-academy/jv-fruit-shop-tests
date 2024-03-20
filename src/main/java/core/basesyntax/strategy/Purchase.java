package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.FruitShopException;

public class Purchase implements OperationHandler {
    @Override
    public void handleFruitOperation(String fruit, Integer quantity) {
        if (Storage.get(fruit) < quantity) {
            throw new FruitShopException("Not enough " + fruit + "we have only" + quantity);
        }
        Storage.put(fruit, Storage.get(fruit) - quantity);
    }
}
