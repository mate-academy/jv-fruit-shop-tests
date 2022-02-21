package core.basesyntax.shop.strategy;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.impl.FruitTransaction;
import core.basesyntax.shop.model.Fruit;

public class SubtractOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        Fruit fruit = new Fruit(fruitTransaction.getFruitName());
        int oldAmount = Storage.fruitsCount.getOrDefault(fruit, 0);
        if (fruitTransaction.getQuantity() > oldAmount) {
            throw new RuntimeException("Not enough product");
        }
        int newAmount = oldAmount - fruitTransaction.getQuantity();
        Storage.fruitsCount.put(fruit, newAmount);
    }
}
