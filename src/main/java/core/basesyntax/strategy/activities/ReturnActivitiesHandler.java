package core.basesyntax.strategy.activities;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.exception.StrategyException;

public class ReturnActivitiesHandler implements ActivitiesHandler {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void changeFruit(String fruitType, Integer amount) {
        if (fruitType == null || amount == null || amount < 0) {
            throw new StrategyException("Invalid input");
        }
        if (storageDao.get(fruitType) == null) {
            storageDao.add(fruitType, amount);
            return;
        }
        int sum = storageDao.get(fruitType) + amount;
        storageDao.add(fruitType, sum);
    }
}
