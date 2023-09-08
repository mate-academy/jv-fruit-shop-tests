package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationActivity;

public class ReturnOperationActivity implements OperationActivity {
    @Override
    public void handleTransaction(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int returnQuantity = transaction.getQuantity();
        if (Storage.fruitTransactionsMap.containsKey(fruit)) {
            int storageQuantity = Storage.fruitTransactionsMap.get(fruit);
            Storage.fruitTransactionsMap.put(fruit, returnQuantity + storageQuantity);
        } else {
            Storage.fruitTransactionsMap.put(fruit, returnQuantity);
        }
    }
}
