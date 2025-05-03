package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int currentQuantity = Storage.getFruitQuantity(fruit);

        if (currentQuantity < fruitTransaction.getQuantity()) {
            throw new IllegalStateException(
                "Not enough stock for fruit: " + fruit + ". Current stock: " + currentQuantity
                    + ", requested: " + fruitTransaction.getQuantity());
        }
        Storage.modifyFruitStorage(fruit, -fruitTransaction.getQuantity());
    }
}
