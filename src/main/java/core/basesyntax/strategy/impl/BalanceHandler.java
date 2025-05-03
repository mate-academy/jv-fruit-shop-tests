package core.basesyntax.strategy.impl;

import static core.basesyntax.db.Storage.modifyFruitStock;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public void doOperation(FruitTransaction transaction) {
        modifyFruitStock(transaction.getFruit(), transaction.getQuantity());
    }
}
