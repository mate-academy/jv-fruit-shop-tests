package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public void operate(FruitTransaction fruitTransaction) {
        int purchase = Storage.fruit.get(fruitTransaction.getFruit());
        if (purchase < fruitTransaction.getQuantity()) {
            throw new RuntimeException("Quantity can't be less than you want buy "
                    + fruitTransaction.getFruit());
        } else {
            Storage.addFruits(fruitTransaction.getFruit(), purchase);
        }
    }
}
