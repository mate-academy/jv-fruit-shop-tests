package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.UnaryOperation;

public class ApplyPurchase implements UnaryOperation {
    @Override
    public void apply(FruitTransaction fruit) {
        if (fruit == null || !Storage.storage.containsKey(fruit.getFruit())
                || fruit.getQuantity() < 0
                || Storage.storage.get(fruit.getFruit()).intValue() < fruit.getQuantity()) {
            throw new FruitStoreException("Can't selling " + (fruit != null
                    ? fruit.getFruit() : "null")
                    + " the quantity of which is less or absent on the balance than in query");
        }
        Storage.storage.put(fruit.getFruit(),
                        Storage.storage.get(fruit.getFruit()).intValue() - fruit.getQuantity());
    }
}
