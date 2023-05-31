package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandlerImpl implements OperationHandler {
    private FruitDao fruitDao;

    public BalanceOperationHandlerImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        fruitDao.add(new Fruit(fruitTransaction.getFruit(), fruitTransaction.getQuantity()));
    }
}
