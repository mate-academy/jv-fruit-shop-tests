package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new IllegalArgumentException("Fruit transaction is null");
        }
        String fruit = fruitTransaction.getFruit();
        if (fruit == null) {
            throw new RuntimeException("Fruit is null");
        }
        int quantity = fruitTransaction.getQuantity();
        storageDao.putToStorage(fruit, quantity);
    }
}
