package service.action.type;

import bd.LocalStorage;
import dao.FruitDao;
import model.Fruit;
import service.action.ActionStrategyHandler;

public class PlusQuantityHandler implements ActionStrategyHandler {
    private final FruitDao fruitDao;

    public PlusQuantityHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public boolean apply(String fruitName, int quantity) {
        Fruit fruit = fruitDao.get(fruitName).orElseGet(() -> {
            Fruit fruit1 = new Fruit(fruitName, 0);
            LocalStorage.fruits.add(fruit1);
            return fruit1;
        });
        fruit.setCount(fruit.getCount() + quantity);
        return true;
    }
}
