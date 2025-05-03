package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;

public class ReturnHandler implements OperationHandler {
    private final StorageDao<Fruit> storageDao;

    public ReturnHandler(StorageDao<Fruit> storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean operate(String fruitName, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Fruits amount can't be less than 0");
        }
        Fruit prevFruit = storageDao.getByName(fruitName);
        int currentQuantity = prevFruit.getQuantity();
        Fruit fruit = new Fruit(fruitName, quantity + currentQuantity);
        return storageDao.update(prevFruit, fruit);
    }
}
