package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int currentQuantity = Storage.fruits.getOrDefault(fruit, 0);
        Storage.fruits.put(fruit, currentQuantity + transaction.getQuantity());
    }
}
