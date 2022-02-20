package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.service.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void performOperation(String fruitName, int amount, StorageDao storageDao) {
        storageDao.add(fruitName, amount);
    }
}
