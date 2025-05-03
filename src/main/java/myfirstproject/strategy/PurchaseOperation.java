package myfirstproject.strategy;

import myfirstproject.dao.FruitDao;
import myfirstproject.model.Fruit;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void changeValue(FruitDao fruitDao, Fruit fruit, int value) {
        fruitDao.save(fruit, fruitDao.getQuantity(fruit) - value);
    }
}
