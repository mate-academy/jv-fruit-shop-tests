package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Integer fruitQuantity = Storage.storage.get(fruitTransaction.getFruit());
        if (fruitTransaction.getFruit() == null || fruitQuantity < fruitTransaction.getQuantity()) {
            throw new RuntimeException("There are no enough fruits in storage");
        }
        Storage.storage.replace(fruitTransaction.getFruit(),
                fruitQuantity - fruitTransaction.getQuantity());
        Storage.storage.get(fruitTransaction.getFruit());
    }
}
