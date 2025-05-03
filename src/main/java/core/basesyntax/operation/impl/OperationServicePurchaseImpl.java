package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exception.FruitLessThanNeedException;
import core.basesyntax.exception.NoSuchFruitInStorageException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationService;

public class OperationServicePurchaseImpl implements OperationService {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        Fruit fruit = fruitTransaction.getFruit();
        if (FruitStorage.storage.containsKey(fruit)) {
            int newCount = FruitStorage.storage.get(fruit) - fruitTransaction.getQuantity();
            if (newCount < 0) {
                throw new FruitLessThanNeedException("Something went wrong."
                        + " There is not enough fruit in storage");
            }
            FruitStorage.storage.put(fruit, newCount);
        } else {
            throw new NoSuchFruitInStorageException("No info about this fruit - "
                    + fruit.getName());
        }
    }
}
