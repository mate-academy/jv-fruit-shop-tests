package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.exception.FruitException;
import core.basesyntax.strategy.OperationStrategy;

public class PlusOperationStrategy extends OperationStrategy {
    public PlusOperationStrategy(FruitDao fruitDao) {
        super(fruitDao);
    }

    @Override
    public void executeOperation(String fruit, int amount) {
        if (fruit == null || amount <= 0) {
            throw new FruitException("Invalid input data");
        }
        fruitDao.put(fruit, fruitDao.get(fruit) + amount);
    }
}
