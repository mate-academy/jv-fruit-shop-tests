package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;

public class SupplierOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public SupplierOperationHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new NullPointerException("Fruit transaction is null");
        }
        String fruit = fruitTransaction.getFruit();
        if (fruit == null) {
            throw new RuntimeException("Fruit is null");
        }
        int quantity = fruitTransaction.getQuantity();
        int currentBalance = storageDao.getQuantityByObjectType(fruit);
        int newBalance = currentBalance + quantity;
        storageDao.putToStorage(fruit, newBalance);
    }
}
