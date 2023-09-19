package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new FruitShopException("Quantity must be greater than zero");
        }
        String fruitName = fruitTransaction.getFruit();
        int transactionQuantity = fruitTransaction.getQuantity();
        int initialQuantity = Storage.STORAGE.get(fruitName);
        if (initialQuantity < transactionQuantity) {
            throw new FruitShopException("Unfortunately, we don't have this amount ");
        }
        Storage.STORAGE.put(
                fruitTransaction.getFruit(), initialQuantity - transactionQuantity);
    }
}
