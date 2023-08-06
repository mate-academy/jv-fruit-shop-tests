package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        checkInputFruitForNull(fruit);
        checkInputQuantityForValidValue(quantity);
        checkActualFruitQuantity(fruit, quantity);
        Storage.storage.put(fruit, getFruitQuantity(fruit) - quantity);
    }

    private int getFruitQuantity(String fruit) {
        return Storage.storage.get(fruit);
    }

    private void checkActualFruitQuantity(String fruit, int quantity) {
        if (getFruitQuantity(fruit) - quantity < 0) {
            throw new InvalidInputDataException("Current fruit quantity is "
                    + getFruitQuantity(fruit));
        }
    }

    private void checkInputFruitForNull(String fruit) {
        if (fruit == null) {
            throw new InvalidInputDataException("Input fruit can't be null!");
        }
    }

    private void checkInputQuantityForValidValue(int quantity) {
        if (quantity <= 0) {
            throw new InvalidInputDataException("Input quantity can't be less or equal to zero!");
        }
    }
}
