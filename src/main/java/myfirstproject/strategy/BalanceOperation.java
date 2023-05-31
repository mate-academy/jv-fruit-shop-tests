package myfirstproject.strategy;

import myfirstproject.dao.FruitDao;
import myfirstproject.model.Fruit;

public class BalanceOperation implements OperationHandler {
    @Override
    public void changeValue(FruitDao fruitDao, Fruit fruit, int value) {
        fruitDao.save(fruit, value);
    }
}
