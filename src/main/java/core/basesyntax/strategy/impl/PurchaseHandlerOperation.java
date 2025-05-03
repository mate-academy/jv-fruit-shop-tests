package core.basesyntax.strategy.impl;

import core.basesyntax.db.DataStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseHandlerOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int transactionQuantity = fruitTransaction.getQuantity();
        int storageQuantity = DataStorage.fruitStorageMap.get(fruit);
        DataStorage.fruitStorageMap.put(fruit, storageQuantity - transactionQuantity);
    }
}
