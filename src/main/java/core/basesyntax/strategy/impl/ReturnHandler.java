package core.basesyntax.strategy.impl;

import static core.basesyntax.db.Storage.getFruitStock;
import static core.basesyntax.db.Storage.modifyFruitStock;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnHandler implements OperationHandler {
    @Override
    public void doOperation(FruitTransaction transaction) {
        int currentQuantity = getFruitStock().getOrDefault(transaction.getFruit(), 0);
        modifyFruitStock(transaction.getFruit(),
                currentQuantity + transaction.getQuantity());
    }
}
