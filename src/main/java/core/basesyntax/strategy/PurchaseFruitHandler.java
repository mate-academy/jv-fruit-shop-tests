package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class PurchaseFruitHandler implements FruitHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        int balance = storage.storage.get(fruitTransaction.getFruit());
        storage.storage.put(fruitTransaction.getFruit(), balance - fruitTransaction.getValue());
    }
}
