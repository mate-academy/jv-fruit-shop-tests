package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class BalanceFruitHandler implements FruitHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        checkTransaction(fruitTransaction);
        storage.storage.put(fruitTransaction.getFruit(), fruitTransaction.getValue());
    }
}
