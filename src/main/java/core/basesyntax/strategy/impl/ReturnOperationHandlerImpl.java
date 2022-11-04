package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandlerImpl implements OperationHandler {
    private FruitDao fruitDao;

    public ReturnOperationHandlerImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Fruit fruit = fruitDao.get(fruitTransaction.getFruit());
        if (fruit != null) {
            fruit.setQuantity(fruit.getQuantity()
                    + fruitTransaction.getQuantity());
        }
    }
}
