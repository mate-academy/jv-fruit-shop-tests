package core.basesyntax.operation;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;

public class PurchaseOperationHandler implements OperationHandler {
    private StorageDao storageDao;

    public PurchaseOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean apply(Fruit fruit, int quantity) {
        Integer quantityFromDB = storageDao.get(fruit);
        if (quantity > (quantityFromDB == null ? 0 : quantityFromDB)) {
            throw new RuntimeException("Shop don't have enough fruit, please peek less than "
                    + quantityFromDB);
        }
        return storageDao.add(fruit, quantityFromDB == null ? 0 : quantityFromDB - quantity);
    }
}
