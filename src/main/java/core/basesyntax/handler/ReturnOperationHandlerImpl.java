package core.basesyntax.handler;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class ReturnOperationHandlerImpl implements OperationHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int quantityToReturn = fruitTransaction.getFruitQuantity();
        int fruitQuantityInStorage = FruitsStorage.fruitsStorage.get(fruitName);
        if (FruitsStorage.fruitsStorage.containsKey(fruitName)) {
            fruitQuantityInStorage = fruitQuantityInStorage + quantityToReturn;
        }
        FruitsStorage.fruitsStorage.put(fruitName, fruitQuantityInStorage);
    }
}
