package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;

public class SupplyOperationHandler implements OperationHandler {
    private StorageDao storageDao;

    public SupplyOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean apply(Fruit fruit, int quantity) {
        Integer quantityFromStorage = storageDao.get(fruit);
        return storageDao.add(fruit,
                quantityFromStorage == null ? quantity : quantityFromStorage + quantity);
    }
}
