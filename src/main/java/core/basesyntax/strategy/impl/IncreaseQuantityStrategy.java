package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitOperationStrategy;

public class IncreaseQuantityStrategy implements FruitOperationStrategy {
    private final FruitDao fruitDao;

    public IncreaseQuantityStrategy(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity should be a non-negative value.");
        }
        String fruitName = transaction.getFruitName();
        int oldQuantity = fruitDao.getQuantityByFruitName(fruitName);
        int increase = transaction.getQuantity();
        int newQuantity = oldQuantity + increase;
        fruitDao.addFruit(fruitName, newQuantity);
    }
}
