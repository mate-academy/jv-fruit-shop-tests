package core.basesyntax.shop.strategy;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.model.Fruit;

public class AddOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        Fruit fruit = new Fruit(fruitTransaction.getFruitName());
        int newAmount = Storage.fruitsCount.getOrDefault(fruit, 0)
                + fruitTransaction.getQuantity();
        Storage.fruitsCount.put(fruit, newAmount);
    }
}
