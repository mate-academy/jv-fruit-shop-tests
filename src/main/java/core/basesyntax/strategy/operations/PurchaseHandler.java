package core.basesyntax.strategy.operations;

import core.basesyntax.db.Storage;

public class PurchaseHandler implements OperationHandler {
    private static final String STORAGE_EMPTY_MESSAGE = "The storage is empty";
    private static final int EMPTY = 0;

    @Override
    public void operateStorage(String fruitType, Integer quantity) {
        Storage.storageContents.put(fruitType,Storage.storageContents.get(fruitType) - quantity);
        if (Storage.storageContents.get(fruitType) < EMPTY) {
            Storage.storageContents.remove(fruitType);
            throw new RuntimeException(STORAGE_EMPTY_MESSAGE);
        }
    }
}
