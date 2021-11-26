package shop.service.action;

import shop.dao.FruitDao;
import shop.model.Fruit;

public class IncreaseActionHandler implements ActionHandler {
    private final FruitDao fruitDao;

    public IncreaseActionHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public boolean update(String fruitName, int count) {
        Fruit fruit = fruitDao.get(fruitName);
        if (fruit == null) {
            fruit = new Fruit(fruitName, 0);
            fruitDao.add(fruit);
        }
        fruit.setCount(fruit.getCount() + count);
        return true;
    }
}
