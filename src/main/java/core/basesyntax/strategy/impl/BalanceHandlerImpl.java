package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandlerImpl implements OperationHandler {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() >= 0) {
            fruitDao.add(transaction.getFruit(), transaction.getQuantity());
        } else {
            throw new RuntimeException("Quantity can't be negative!");
        }
    }
}
