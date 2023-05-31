package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;

public class OperationHandlerSupplyImpl implements OperationHandler {
    private static final int NON_NEGATIVE_AMOUNT = 0;
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public void apply(Fruit fruit, int amount) {
        if (amount < NON_NEGATIVE_AMOUNT) {
            throw new RuntimeException("we can not supply negative amount of fruits");
        }
        if (new StorageImpl().getStorage().containsKey(fruit)) {
            int balance = fruitDao.getAmountCurrentFruitInShop(fruit);
            fruitDao.update(fruit, balance + amount);
        } else {
            fruitDao.put(fruit, amount);
        }
    }
}
