package core.basesyntax.strategy.impl;

import core.basesyntax.db.DataStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnHandlerOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int quantityFruits = fruitTransaction.getQuantity();
        int previousQuantity = DataStorage.fruitStorageMap.get(fruit);
        DataStorage.fruitStorageMap
                .merge(fruit, quantityFruits, (oldVal, value)
                        -> quantityFruits + previousQuantity);
    }
}
