package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public PurchaseOperationHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (!fruitTransaction.getOperation().equals("p")) {
            throw new RuntimeException("Unknown operation - " + fruitTransaction.getOperation());
        }
        String fruit = fruitTransaction.getFruit();
        fruitStorageDao.remove(fruit, fruitTransaction.getQuantity());
    }
}
