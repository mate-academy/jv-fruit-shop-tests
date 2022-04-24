package basesyntax.service.strategy.handlers;

import basesyntax.dao.StorageDao;
import basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public SupplyOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        storageDao.addToDataBase(fruitTransaction.getFruit(),
                storageDao.getByKey(fruitTransaction.getFruit())
                        + fruitTransaction.getQuantity());
    }
}
