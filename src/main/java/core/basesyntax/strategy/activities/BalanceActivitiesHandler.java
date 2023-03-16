package core.basesyntax.strategy.activities;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.exception.ServiceException;

public class BalanceActivitiesHandler implements ActivitiesHandler {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void changeFruit(String fruitType, Integer amount) {
        if (storageDao.get(fruitType) == null) {
            storageDao.add(fruitType, amount);
            return;
        }
        if (amount < 0) {
            throw new ServiceException("Can't accept negative numbers " + amount);
        }
        int sum = storageDao.get(fruitType) + amount;
        storageDao.add(fruitType, sum);
    }
}
