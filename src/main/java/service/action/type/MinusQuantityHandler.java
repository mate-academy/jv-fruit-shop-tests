package service.action.type;

import dao.FruitDao;
import model.Fruit;
import service.action.ActionStrategyHandler;

public class MinusQuantityHandler implements ActionStrategyHandler {
    private final FruitDao fruitDao;

    public MinusQuantityHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public boolean apply(String fruitName, int quantity) {
        Fruit fruit = fruitDao.get(fruitName).orElseThrow(() ->
                new RuntimeException("there is no such vegetable to sell"));
        if (fruit.getCount() < quantity) {
            throw new RuntimeException("the quantity of goods cannot be fruit");
        }
        fruit.setCount(fruit.getCount() - quantity);
        return true;
    }
}
