package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private static final String EXCEPTION_MESSAGE =
            "Purchased quantity can't be more than available";
    private final StorageDao storageDao;

    public PurchaseOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        int updatedQuantity = storageDao.getQuantity(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
        storageDao.add(fruitTransaction.getFruit(), updatedQuantity);
    }
}
