package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;

public class AddOperationHandler implements OperationHandler {
    @Override
    public int apply(FruitTransaction fruitTransaction) {
        int previousValue = Storage.fruits.getOrDefault(fruitTransaction.getFruit(), 0);
        int newValue = previousValue + fruitTransaction.getQuantity();

        if (newValue < 0) {
            // If the new quantity is negative, do not add the fruit to the storage
            newValue = 0;
        } else {
            Storage.fruits.put(fruitTransaction.getFruit(), newValue);
        }

        return newValue;
    }

}
