package core.basesyntax.store.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;

public class BalanceHandler implements TypeHandler {
    private final FruitDao fruitDao;

    public BalanceHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void makeOperation(String fruitName, long quantity) {
        fruitDao.add(new Fruit(fruitName, quantity));
    }
}
