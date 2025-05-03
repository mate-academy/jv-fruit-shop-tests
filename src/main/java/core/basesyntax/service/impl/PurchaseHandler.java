package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationHandler;

public class PurchaseHandler implements OperationHandler {
    private final StorageDao<Fruit> storageDao;

    public PurchaseHandler(StorageDao<Fruit> storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public boolean operate(String fruitName, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Fruits amount can't be less than 0");
        }
        Fruit prevFruit = storageDao.getByName(fruitName);
        int currentQuantity = prevFruit.getQuantity();
        if (currentQuantity < quantity) {
            throw new RuntimeException("Not enough fruits to buy");
        }
        Fruit fruit = new Fruit(fruitName, currentQuantity - quantity);
        return storageDao.update(prevFruit, fruit);
    }
}
