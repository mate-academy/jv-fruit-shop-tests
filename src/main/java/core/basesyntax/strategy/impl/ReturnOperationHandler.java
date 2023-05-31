package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    private final FruitStorageDao fruitStorageDao;

    public ReturnOperationHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (!fruitTransaction.getOperation().equals("r")) {
            throw new RuntimeException("Unknown operation - " + fruitTransaction.getOperation());
        }
        String fruit = fruitTransaction.getFruit();
        fruitStorageDao.add(fruit, fruitTransaction.getQuantity());
    }
}
