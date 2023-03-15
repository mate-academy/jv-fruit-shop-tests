package core.basesyntax.strategy.activities;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;

public class PurchaseActivitiesHandler implements ActivitiesHandler {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void changeFruit(String fruitType, Integer amount) {
        if (storageDao.get(fruitType) == null) {
            throw new RuntimeException("Can't find fruit");
        }
        int different = storageDao.get(fruitType) - amount;
        storageDao.add(fruitType, different);
    }
}
