package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Objects;

public class ReturnOperationHandler implements OperationHandler {
    private final StorageDao storageDao;

    public ReturnOperationHandler(StorageDao storageDao) {
        Objects.requireNonNull(storageDao);
        this.storageDao = storageDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        Objects.requireNonNull(transaction);
        int currentQuantity = storageDao.getAmountByProductName(transaction.fruit());
        storageDao.putProduct(transaction.fruit(), currentQuantity + transaction.quantity());
    }
}
