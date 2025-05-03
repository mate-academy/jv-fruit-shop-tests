package core.basesyntax.service.strategy;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    private FruitStorageDao storageDao;

    public BalanceOperation(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public int handle(FruitTransaction transaction) {
        return storageDao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
