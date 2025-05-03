package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        checkInputFruitForNull(fruit);
        checkInputQuantityForValidValue(quantity);
        Storage.storage.put(fruit, Storage.storage.get(fruit) + quantity);
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
