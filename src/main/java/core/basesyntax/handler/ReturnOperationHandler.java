package core.basesyntax.handler;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public ReturnOperationHandler(StorageDao storageDao) {
        if (storageDao == null) {
            throw new RuntimeException("Storage dao can't be null");
        }
        this.storageDao = storageDao;
    }

    @Override
    public void applyOperation(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction can't be null");
        }
        Integer previousValue = storageDao.get(transaction.getFruit());
        storageDao.set(transaction.getFruit(), previousValue != null
                ? previousValue + transaction.getQuantity()
                : transaction.getQuantity());
    }
}
