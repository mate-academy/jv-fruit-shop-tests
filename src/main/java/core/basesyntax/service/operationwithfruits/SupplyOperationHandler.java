package core.basesyntax.service.operationwithfruits;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.service.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public SupplyOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void getOperation(FruitTransaction transaction) {
        if (storageDao.getCountFruit(transaction.getFruit()) != Integer.MAX_VALUE) {
            storageDao.update(transaction.getFruit(),
                    storageDao.getCountFruit(transaction.getFruit())
                            + transaction.getQuantity());
        } else {
            throw new RuntimeException("Value cannot be added "
                    + "- the database contains the maximum value for this type!");
        }
    }
}
