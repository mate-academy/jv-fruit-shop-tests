package core.basesyntax.strategy.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.strategy.DoActivities;

public class BalanceReadActivity implements DoActivities {
    private FruitStorageDao storageDao;

    public BalanceReadActivity(FruitStorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void doActivity(String fruit, Integer number) {
        storageDao.save(fruit, number);
    }
}
