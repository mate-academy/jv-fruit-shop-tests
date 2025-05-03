package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ActivityHandler;

public class PurchaceActivityHandler implements ActivityHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentAmount = FruitStorage.fruitStorage.get(transaction.getFruit());
        FruitStorage.fruitStorage.put(
                transaction.getFruit(), currentAmount - transaction.getAmount());
    }
}

