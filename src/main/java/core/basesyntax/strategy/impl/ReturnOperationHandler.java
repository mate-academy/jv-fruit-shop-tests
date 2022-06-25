package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;

public class ReturnOperationHandler implements FruitHandler {
    private FruitsDao fruitsDao;

    public ReturnOperationHandler(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (transaction == null
                || !transaction.getOperation().equals(FruitTransaction.Operation.RETURN)) {
            throw new RuntimeException("You need to indicate proper transaction");
        }
        int amountFromStorage = fruitsDao.get(transaction.getFruit());
        int newAmountOfStorage = amountFromStorage + transaction.getQuantity();
        fruitsDao.add(transaction.getFruit(), newAmountOfStorage);
    }
}
