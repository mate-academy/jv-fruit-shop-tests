package core.basesyntax.handler;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.DataValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class PurchaseHandlerImpl implements OperationHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        int quantityToSubtract = fruitTransaction.getFruitQuantity();
        int fruitQuantityInStorage = FruitsStorage.fruitsStorage.get(fruitName);
        if (fruitQuantityInStorage < quantityToSubtract) {
            throw new DataValidationException("The quantity of the product "
                    + "for purchase exceeds the quantity available in the storage");
        }
        if (FruitsStorage.fruitsStorage.containsKey(fruitName)) {
            fruitQuantityInStorage = fruitQuantityInStorage - quantityToSubtract;
        }
        FruitsStorage.fruitsStorage.put(fruitName, fruitQuantityInStorage);
    }
}
