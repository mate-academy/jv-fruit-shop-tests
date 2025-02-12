package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        int currentQuantity = Storage.getFruitStorage().getOrDefault(fruit, 0);
        Storage.getFruitStorage().put(fruit, quantity + currentQuantity);
    }
}
