package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public boolean makeOperation(FruitTransaction transaction) {
        if (transaction.getOperation() != FruitTransaction.Operation.BALANCE) {
            throw new RuntimeException("You try to use incorrect operation instead BALANCE "
                                        + transaction.getOperation());
        }
        return fruitDao.update(transaction.getFruit(), transaction.getQuantity());
    }
}
