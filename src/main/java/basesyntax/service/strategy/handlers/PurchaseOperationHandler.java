package basesyntax.service.strategy.handlers;

import basesyntax.dao.StorageDao;
import basesyntax.model.FruitTransaction;
import basesyntax.model.NotEnoughFruitsInStorage;

public class PurchaseOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public PurchaseOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        if (storageDao.getByKey(fruitTransaction.getFruit()) < fruitTransaction.getQuantity()) {
            throw new NotEnoughFruitsInStorage("Can't do this operation");
        }
        storageDao.addToDataBase(fruitTransaction.getFruit(),
                storageDao.getByKey(fruitTransaction.getFruit()) - fruitTransaction.getQuantity());
    }
}
