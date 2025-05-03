package core.basesyntax.strategy.transactions.impl;

import core.basesyntax.db.StorageDao;
import core.basesyntax.strategy.transactions.FruitTransaction;
import core.basesyntax.strategy.transactions.TransactionHandler;

public class ReduceHandler implements TransactionHandler {
    private final StorageDao storageDao;

    public ReduceHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void apply(FruitTransaction fruitTransaction) {
        Integer oldValue = storageDao.getValue(fruitTransaction.getFruitName());
        Integer newValue = oldValue - fruitTransaction.getValueOfFruit();
        if (newValue < 0) {
            throw new RuntimeException("You put wrong data. "
                    + "Value in storage can`t be less 0");
        }
        storageDao.setValue(fruitTransaction.getFruitName(),
                newValue);
    }
}
