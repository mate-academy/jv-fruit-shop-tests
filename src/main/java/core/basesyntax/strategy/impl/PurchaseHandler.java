package core.basesyntax.strategy.impl;

import static core.basesyntax.db.Storage.modifyFruitStock;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void doOperation(FruitTransaction transaction) {
        int currentQuantity = Storage.getFruitStock().getOrDefault(transaction.getFruit(), 0);
        if (currentQuantity < transaction.getQuantity()) {
            throw new IllegalArgumentException("Not enough fruits in stock! Currently in stock: "
                    + currentQuantity);
        }
        modifyFruitStock(transaction.getFruit(),
                currentQuantity - transaction.getQuantity());
    }
}
