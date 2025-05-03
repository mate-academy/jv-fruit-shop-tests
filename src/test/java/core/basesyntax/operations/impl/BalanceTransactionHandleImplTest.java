package core.basesyntax.operations.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.TransactionHandle;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceTransactionHandleImplTest {
    private static TransactionHandle transactionHandle;
    private static StorageDao storage;

    @BeforeClass
    public static void beforeClass() {
        storage = new StorageDaoImpl();
        transactionHandle = new BalanceTransactionHandleImpl(storage);
    }

    @Test
    public void balanceTransaction_equalsExecuteOperation() {
        FruitTransaction actual =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        transactionHandle.executeTransaction(actual);
        Integer excepted = 100;
        Assert.assertEquals(excepted, Storage.storage.get("banana"));
    }
}
