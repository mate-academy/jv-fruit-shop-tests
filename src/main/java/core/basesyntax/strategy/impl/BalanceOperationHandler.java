package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        if (storageDao == null) {
            throw new IllegalInputDataException("Storage dao can`t be null");
        }

        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction == null) {
            throw new IllegalInputDataException("Fruit transaction dao can`t be null");
        }

        storageDao.putProduct(transaction.fruit(), transaction.quantity());
    }
}
