package core.basesyntax.strategy.activities;

import core.basesyntax.db.FruitStore;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements ActivitiesHandler {
    @Override
    public void operation(FruitTransaction fruitTransaction) {
        if (FruitStore.supplies.containsKey(fruitTransaction.getFruit())
                && FruitStore.supplies.get(fruitTransaction.getFruit())
                >= fruitTransaction.getQuantity()) {
            int quantity = FruitStore.supplies.get(fruitTransaction.getFruit())
                    - fruitTransaction.getQuantity();
            FruitStore.supplies.put(fruitTransaction.getFruit(), quantity);
        } else {
            throw new RuntimeException("there are no such fruit in stock");
        }
    }
}
