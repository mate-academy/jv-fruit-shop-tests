package core.strategy.impl;

import core.dao.FruitDao;
import core.model.Fruit;
import core.strategy.OperationHandler;

public class AddOperationImpl implements OperationHandler {
    private FruitDao fruitDao;

    public AddOperationImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(String name, int quantity) {
        Fruit fruit = fruitDao.get(name);
        int newQuantity = fruit.getQuantity() + quantity;
        fruit.setQuantity(newQuantity);
    }
}
