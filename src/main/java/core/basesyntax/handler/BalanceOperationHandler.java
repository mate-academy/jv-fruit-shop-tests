package core.basesyntax.handler;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
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
        storageDao.set(transaction.getFruit(), transaction.getQuantity());
    }
}
