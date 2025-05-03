package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.exception.FruitException;
import core.basesyntax.strategy.OperationStrategy;

public class MinusOperationStrategy extends OperationStrategy {
    public MinusOperationStrategy(FruitDao fruitDao) {
        super(fruitDao);
    }

    @Override
    public void executeOperation(String fruit, int amount) {
        if (fruit == null || amount <= 0) {
            throw new FruitException("Invalid input data");
        }
        int currentAmount = fruitDao.get(fruit);
        if (currentAmount < amount) {
            throw new FruitException("In storage only " + fruitDao.get(fruit) + " "
                    + fruit + ". Amount operation " + amount);
        }
        fruitDao.put(fruit, currentAmount - amount);
    }
}
