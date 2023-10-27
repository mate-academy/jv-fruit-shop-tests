package core.basesyntax.service.handler.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void updateStorage(FruitTransaction transaction) {
        validAmount(transaction);
        String fruitName = transaction.getFruitName();
        if (!storageDao.isInStorage(fruitName)) {
            storageDao.add(new Fruit(fruitName), transaction.getAmount());
        }
    }
}
