package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import java.util.NoSuchElementException;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void set(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        if (Storage.fruits.get(fruit) == null) {
            throw new NoSuchElementException("This fruit is not in stock: " + fruit);
        }
        Storage.fruits.put(fruit, Storage.fruits.get(fruit) - fruitTransaction.getQuantity());
    }
}
