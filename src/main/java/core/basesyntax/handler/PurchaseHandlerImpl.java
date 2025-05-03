package core.basesyntax.handler;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.DataValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class PurchaseHandlerImpl implements OperationHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int quantityToSubtract = fruitTransaction.getFruitQuantity();
        int fruitQuantityInStorage = FruitsStorage.FRUITS_STORAGE.get(fruitName);
        if (fruitQuantityInStorage < quantityToSubtract) {
            throw new DataValidationException("The quantity of the product "
                    + "for purchase exceeds the quantity available in the storage");
        }
        if (FruitsStorage.FRUITS_STORAGE.containsKey(fruitName)) {
            fruitQuantityInStorage = fruitQuantityInStorage - quantityToSubtract;
        }
        FruitsStorage.FRUITS_STORAGE.put(fruitName, fruitQuantityInStorage);
    }
}
