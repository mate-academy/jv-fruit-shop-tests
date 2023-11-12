package core.basesyntax.handler;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class ReturnOperationHandlerImpl implements OperationHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int quantityToReturn = fruitTransaction.getFruitQuantity();
        int fruitQuantityInStorage = FruitsStorage.FRUITS_STORAGE.get(fruitName);
        if (FruitsStorage.FRUITS_STORAGE.containsKey(fruitName)) {
            fruitQuantityInStorage = fruitQuantityInStorage + quantityToReturn;
        }
        FruitsStorage.FRUITS_STORAGE.put(fruitName, fruitQuantityInStorage);
    }
}
