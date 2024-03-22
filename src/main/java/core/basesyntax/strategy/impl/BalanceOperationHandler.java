package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Objects;

public class BalanceOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public BalanceOperationHandler(StorageDao storageDao) {
        Objects.requireNonNull(storageDao);
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        Objects.requireNonNull(transaction);
        storageDao.putProduct(transaction.fruit(), transaction.quantity());
    }
}
