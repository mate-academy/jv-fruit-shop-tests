package core.basesyntax.service.impl.operation;

import core.basesyntax.dao.FruitStorageDao;

public class BalanceOperation implements OperationHandler {
    private final FruitStorageDao storageDao;

    public BalanceOperation(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doOperation(String fruitName, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative for balance operation: " + quantity
            );
        }
        storageDao.setQuantity(fruitName, quantity);
    }
}
