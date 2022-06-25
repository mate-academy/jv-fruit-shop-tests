package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;

public class PurchaseOperationHandler implements FruitHandler {
    private FruitsDao fruitsDao;

    public PurchaseOperationHandler(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (transaction == null
                || transaction.getQuantity() < 0
                || !transaction.getOperation().equals(FruitTransaction.Operation.PURCHASE)) {
            throw new RuntimeException("You need to indicate valid data");
        }
        int amountFromStorage = fruitsDao.get(transaction.getFruit());
        if (amountFromStorage < transaction.getQuantity()) {
            throw new RuntimeException(
                    "Not enough fruits for purchasing. Only "
                            + fruitsDao.get(transaction.getFruit()) + " available");
        }
        int newAmountFromStorage = amountFromStorage - transaction.getQuantity();
        fruitsDao.add(transaction.getFruit(), newAmountFromStorage);
    }
}
