package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.ShopOperationHandler;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements ShopOperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity should not be less than zero. Current quantity is "
                    + fruitTransaction.getQuantity());
        }
        Storage.fruitStorage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
