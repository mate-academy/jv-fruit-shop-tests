package myfirstproject.strategy;

import myfirstproject.dao.FruitDao;
import myfirstproject.model.Fruit;

public interface OperationHandler {
    void changeValue(FruitDao fruitDao, Fruit fruit, int value);
}
