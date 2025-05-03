package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ActivityHandler;

public class BalanceActivityHandler implements ActivityHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        FruitStorage.fruitStorage.put(transaction.getFruit(), transaction.getAmount());
    }
}
