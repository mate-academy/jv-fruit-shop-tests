package core.basesyntax.handler;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class BalanceHandlerImpl implements OperationHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int fruitQuantity = fruitTransaction.getFruitQuantity();
        if (FruitsStorage.FRUITS_STORAGE.containsKey(fruitName)) {
            int fruitQuantityInStorage = FruitsStorage.FRUITS_STORAGE.get(fruitName);
            fruitQuantity = fruitQuantity + fruitQuantityInStorage;
        }
        FruitsStorage.FRUITS_STORAGE.put(fruitName, fruitQuantity);
    }
}
