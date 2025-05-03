package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Can't return negative value...");
        }
        Integer currentQuantity = Storage.getFruitQuantity(fruitTransaction.getFruit());
        Storage.setFruitQuantity(fruitTransaction.getFruit(),
                currentQuantity + fruitTransaction.getQuantity());
    }
}
