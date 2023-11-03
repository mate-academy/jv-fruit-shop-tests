package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    private static final String ERROR_MESSAGE = "You can't supply a negative quantity of fruits!";
    private final FruitStorageDao fruitStorageDao;

    public SupplyOperationHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void operate(String fruit, int quantity) {
        if (quantity < 0 || fruitStorageDao.getQuantity(fruit) == -1) throw new IllegalArgumentException(ERROR_MESSAGE);
        int updatedQuantity = fruitStorageDao.getQuantity(fruit) + quantity;
        fruitStorageDao.add(fruit, updatedQuantity);
    }
}
