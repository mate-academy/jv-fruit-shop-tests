package basesyntax.service.strategy.handlers;

import basesyntax.dao.StorageDao;
import basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public ReturnOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        storageDao.addToDataBase(fruitTransaction.getFruit(),
                storageDao.getByKey(fruitTransaction.getFruit()) + fruitTransaction.getQuantity());
    }
}
