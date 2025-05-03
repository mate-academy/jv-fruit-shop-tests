package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.UnaryOperation;

public class ApplySupplay implements UnaryOperation {
    @Override
    public void apply(FruitTransaction fruit) {
        if (fruit == null) {
            throw new FruitStoreException("Incorrect input data for adding to storage");
        }
        if (Storage.storage.containsKey(fruit.getFruit())) {
            Storage.storage.put(fruit.getFruit(),
                    Storage.storage.get(fruit.getFruit()).intValue() + fruit.getQuantity());
        } else {
            Storage.storage.put(fruit.getFruit(),fruit.getQuantity());
        }
    }
}
