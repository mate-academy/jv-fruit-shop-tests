package core.basesyntax.transactionhandler.impl;

import core.basesyntax.FruitShopException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.transactionhandler.TransactionHandler;
import java.util.Objects;

public class BalanceHandler implements TransactionHandler {
    private final StorageDao storageDao;

    public BalanceHandler(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new FruitShopException("Transaction can't be null");
        }
        if (transaction.getQuantity() < 0) {
            throw new FruitShopException("Operation value cannot be negative");
        }
        storageDao.add(transaction.getFruit(), transaction.getQuantity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceHandler that = (BalanceHandler) o;
        return Objects.equals(storageDao, that.storageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storageDao);
    }
}
