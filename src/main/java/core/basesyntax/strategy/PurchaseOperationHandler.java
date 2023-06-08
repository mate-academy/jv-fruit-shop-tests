package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction transaction) {
        int quantityPurchase = Storage.fruits
                .get(transaction.getFruit()) - transaction.getQuantity();
        if (quantityPurchase < 0) {
            throw new RuntimeException("For purchase don't enough " + (-quantityPurchase)
                    + " \"" + transaction.getFruit() + "\" in the storage");
        }
        Storage.fruits.put(transaction.getFruit(), quantityPurchase);
    }
}
