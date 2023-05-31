package core.basesyntax.handler.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    private FruitDao fruitDao;

    public BalanceOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        fruitDao.update(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
