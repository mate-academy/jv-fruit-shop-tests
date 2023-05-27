package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void set(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        if (Storage.fruits.get(fruit) == null) {
            Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        } else {
            Storage.fruits.put(fruit, Storage.fruits.get(fruit) + fruitTransaction.getQuantity());
        }
    }
}
