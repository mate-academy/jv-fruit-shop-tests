package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (FruitStorage.fruitStorage.containsKey(transaction.getFruit())) {
            throw new RuntimeException("Table already have a "
                        + transaction.getFruit() + " balance");
        }
        FruitStorage.fruitStorage
                .put(transaction.getFruit(), transaction.getQuantity());
    }
}
