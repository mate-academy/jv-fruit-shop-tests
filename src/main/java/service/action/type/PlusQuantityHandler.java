package service.action.type;

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
            Fruit newFruit = new Fruit(fruitName, 0);
            fruitDao.add(newFruit);
            return newFruit;
        });
        fruit.setCount(fruit.getCount() + quantity);
        return true;
    }
}
