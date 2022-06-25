package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;

public class BalanceOperationHandler implements FruitHandler {
    private FruitsDao fruitsDao;

    public BalanceOperationHandler(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (transaction == null
                || !transaction.getOperation().equals(FruitTransaction.Operation.BALANCE)) {
            throw new RuntimeException("You need to indicate proper transaction");
        }
        fruitsDao.add(transaction.getFruit(), transaction.getQuantity());
    }
}
