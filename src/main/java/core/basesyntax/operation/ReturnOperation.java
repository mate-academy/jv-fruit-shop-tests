package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.base.Storage;

public class ReturnOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        int currentQuantity = (int) Storage.fruitStorage.getOrDefault(fruit, 0);
        Storage.fruitStorage.put(fruit, quantity + currentQuantity);
    }
}
