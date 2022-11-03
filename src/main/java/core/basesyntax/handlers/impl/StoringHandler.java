package core.basesyntax.handlers.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;

public class StoringHandler {
    private final StorageDao storageDao;

    public StoringHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantityStored = storageDao.getQuantity(fruit).orElse(0);
        storageDao.update(fruit, quantityStored + transaction.getQuantity());
    }
}
