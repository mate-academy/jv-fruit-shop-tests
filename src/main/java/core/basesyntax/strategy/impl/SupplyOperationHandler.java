package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new FruitShopException("The quantity of fruits must be greater than zero");
        }
        int oldQuantity = Storage.FRUITS.get(fruitTransaction.getFruit());
        int transactionQuantity = fruitTransaction.getQuantity();
        Storage.FRUITS.put(fruitTransaction.getFruit(), oldQuantity + transactionQuantity);
    }
}
