package core.basesyntax.shop.strategy;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.model.Fruit;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        int fruitAmount = fruitTransaction.getQuantity();
        Storage.fruitsCount.put(new Fruit(fruitTransaction.getFruitName()), fruitAmount);
    }
}
