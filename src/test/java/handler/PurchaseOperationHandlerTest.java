package handler;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static Storage storage;
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        storageDao = new StorageDaoImpl(storage);
        operationHandler = new PurchaseOperationHandler(storageDao);
    }

    @Test
    public void purchaseIs_Ok() {
        storageDao.update("banana", 100);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 80;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
