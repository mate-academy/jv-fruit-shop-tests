package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;

public class BalanceHandler implements OperationHandler {
    private final StorageDao<Fruit> storageDao;

    public BalanceHandler(StorageDao<Fruit> storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean operate(String fruitName, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Fruits amount can't be less than 0");
        }
        Fruit fruit = new Fruit(fruitName, quantity);
        return storageDao.add(fruit);
    }
}
