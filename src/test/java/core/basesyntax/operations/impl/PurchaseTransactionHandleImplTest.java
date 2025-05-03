package core.basesyntax.operations.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.TransactionHandle;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTransactionHandleImplTest {
    private static StorageDao storage;
    private static TransactionHandle transactionHandle;

    @BeforeClass
    public static void beforeClass() {
        storage = new StorageDaoImpl();
        transactionHandle = new PurchaseTransactionHandleImpl(storage);
    }

    @Test
    public void purchaseTransaction_equalsExecuteOperation() {
        Storage.storage.put("banana", 30);
        FruitTransaction actual =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        transactionHandle.executeTransaction(actual);
        Integer excepted = 20;
        Assert.assertEquals(excepted, Storage.storage.get("banana"));
    }
}
