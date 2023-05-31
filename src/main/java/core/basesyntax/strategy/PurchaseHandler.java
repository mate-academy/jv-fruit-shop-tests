package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("FruitTransaction can't be null");
        }
        String fruitName = fruitTransaction.getFruit();
        int quantityOfParchedFruits = fruitTransaction.getQuantity();
        int oldQuantity = Storage.fruits.get(fruitName);
        Storage.fruits.put(fruitName, oldQuantity - quantityOfParchedFruits);
    }
}
