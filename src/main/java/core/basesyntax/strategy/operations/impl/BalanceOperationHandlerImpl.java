package core.basesyntax.strategy.operations.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operations.OperationHandler;

public class BalanceOperationHandlerImpl implements OperationHandler {
    private StorageDao storageDao;

    public BalanceOperationHandlerImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handler(FruitTransaction fruitTransaction) {
        storageDao.update(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
