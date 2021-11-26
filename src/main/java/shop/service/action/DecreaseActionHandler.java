package shop.service.action;

import shop.dao.FruitDao;
import shop.model.Fruit;

public class DecreaseActionHandler implements ActionHandler {
    private final FruitDao fruitDao;

    public DecreaseActionHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public boolean update(String fruitName, int count) {
        Fruit fruit = fruitDao.get(fruitName);
        if (fruit == null) {
            throw new RuntimeException("Storage don't have this fruit " + fruitName);
        }
        if (fruit.getCount() < count) {
            throw new RuntimeException("U can't sell to many, u have " + fruit.getCount());
        }
        fruit.setCount(fruit.getCount() - count);
        return true;
    }
}
