package core.basesyntax.strategy.activities;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.exception.StrategyException;

public class PurchaseActivitiesHandler implements ActivitiesHandler {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void changeFruit(String fruitType, Integer amount) {
        if (fruitType == null || amount == null || amount < 0) {
            throw new StrategyException("Invalid input");
        }
        if (storageDao.get(fruitType) == null || storageDao.get(fruitType) < amount) {
            throw new StrategyException("Can't find fruit");
        }
        int different = storageDao.get(fruitType) - amount;
        storageDao.add(fruitType, different);
    }
}
