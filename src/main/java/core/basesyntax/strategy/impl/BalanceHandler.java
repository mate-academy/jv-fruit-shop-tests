package core.basesyntax.strategy.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        if (fruit != null) {
            Storage.fruits.put(fruit, quantity);
        } else {
            throw new InvalidDataException("Fruit is not exist");
        }
    }
}
