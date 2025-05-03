package core.basesyntax.transactionhandler.impl;

import core.basesyntax.FruitShopException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.transactionhandler.TransactionHandler;
import java.util.Objects;

public class SupplyHandler implements TransactionHandler {
    private final StorageDao storageDao;

    public SupplyHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new FruitShopException("Transaction can't be null");
        }
        if (transaction.getQuantity() <= 0) {
            throw new FruitShopException("Operation value must be positive");
        }
        int currentQuantity = storageDao.getQuantity(transaction.getFruit());
        int newQuantity = currentQuantity + transaction.getQuantity();
        storageDao.add(transaction.getFruit(), newQuantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupplyHandler that = (SupplyHandler) o;
        return Objects.equals(storageDao, that.storageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storageDao);
    }
}
