package basesyntax.service.strategy.handlers;

import basesyntax.dao.StorageDao;
import basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        storageDao.addToDataBase(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
