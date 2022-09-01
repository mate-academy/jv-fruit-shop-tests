package core.basesyntax.operations.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.TransactionHandle;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnTransactionHandleImplTest {
    private static StorageDao storage;
    private static TransactionHandle transactionHandle;

    @BeforeClass
    public static void beforeClass() {
        storage = new StorageDaoImpl();
        transactionHandle = new ReturnTransactionHandleImpl(storage);
    }

    @Test
    public void returnTransaction_equalsExecuteOperation() {
        Storage.storage.put("banana", 30);
        FruitTransaction actual =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 16);
        transactionHandle.executeTransaction(actual);
        Integer excepted = 46;
        Assert.assertEquals(excepted, Storage.storage.get("banana"));
    }
}
