package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;

public abstract class OperationStrategy {
    protected final FruitDao fruitDao;

    public OperationStrategy(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    public abstract void executeOperation(String fruit, int amount);
}
