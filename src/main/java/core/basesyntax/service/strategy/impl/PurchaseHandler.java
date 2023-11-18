package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class PurchaseHandler implements OperationHandler {

    private final StorageDao storageDao;

    public PurchaseHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public Integer processTransaction(FruitTransaction transaction) {
        int balance = 0;
        if (storageDao.getAmount(transaction.getFruit()) != null) {
            balance = storageDao.getAmount(transaction.getFruit());
        }

        balance -= transaction.getQuantity();

        return Math.max(balance, 0);
    }
}
