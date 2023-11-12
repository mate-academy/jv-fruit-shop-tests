package core.basesyntax.handler;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class SupplierHandlerImpl implements OperationHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int fruitQuantityToAdd = fruitTransaction.getFruitQuantity();
        if (FruitsStorage.FRUITS_STORAGE.containsKey(fruitName)) {
            int fruitQuantityInStorage = FruitsStorage.FRUITS_STORAGE.get(fruitName);
            fruitQuantityToAdd = fruitQuantityToAdd + fruitQuantityInStorage;
        }
        FruitsStorage.FRUITS_STORAGE.put(fruitName, fruitQuantityToAdd);
    }
}
