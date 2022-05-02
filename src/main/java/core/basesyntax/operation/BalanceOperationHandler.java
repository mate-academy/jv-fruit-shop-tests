package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;

public class BalanceOperationHandler implements OperationHandler {
    private StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean apply(Fruit fruit, int quantity) {
        return storageDao.add(fruit, quantity);
    }
}
