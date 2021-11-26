package core.strategy.impl;

import core.dao.FruitDao;
import core.model.Fruit;
import core.strategy.OperationHandler;

public class BalanceOperationImpl implements OperationHandler {
    private FruitDao fruitDao;

    public BalanceOperationImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(String name, int quantity) {
        Fruit fruit = new Fruit(name, quantity);
        fruitDao.add(fruit);
    }
}
