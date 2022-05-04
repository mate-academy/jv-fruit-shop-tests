package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;

public class ReturnOperationHandler implements OperationHandler {
    private StorageDao storageDao;

    public ReturnOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean apply(Fruit fruit, int quantity) {
        if (storageDao.get(fruit) == null) {
            throw new RuntimeException("Can't return " + fruit);
        }
        return storageDao.add(fruit, storageDao.get(fruit) + quantity);
    }
}
