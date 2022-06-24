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
        int amountFromStorage = fruitsDao.get(transaction.getFruit());
        if (amountFromStorage < transaction.getQuantity()) {
            throw new RuntimeException(
                    "Not enough fruits for purchasing. Only "
                            + transaction.getQuantity() + " available");
        }
        int newAmountFromStorage = amountFromStorage - transaction.getQuantity();
        fruitsDao.add(transaction.getFruit(), newAmountFromStorage);
    }
}
