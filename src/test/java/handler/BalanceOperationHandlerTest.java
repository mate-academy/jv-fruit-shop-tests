package handler;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static Storage storage;
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        storageDao = new StorageDaoImpl(storage);
        operationHandler = new BalanceOperationHandler(storageDao);
    }

    @Test
    public void balanceOperation_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 20;
        Assert.assertEquals(expected,actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
