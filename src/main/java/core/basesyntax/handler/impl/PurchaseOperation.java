package core.basesyntax.handler.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    private FruitDao fruitDao;

    public PurchaseOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        fruitDao.update(fruit,
                fruitDao.getQuantity(fruit) - fruitTransaction.getQuantity());
    }
}
