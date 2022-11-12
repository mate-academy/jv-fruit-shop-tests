package myfirstproject.service.impl;

import myfirstproject.dao.FruitDao;
import myfirstproject.model.Fruit;
import myfirstproject.strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void changeValue(FruitDao fruitDao, Fruit fruit, int value) {
        fruitDao.save(fruit, fruitDao.getQuantity(fruit) - value);
    }
}
