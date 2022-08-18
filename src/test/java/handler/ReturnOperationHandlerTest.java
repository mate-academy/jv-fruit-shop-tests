package handler;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static Storage storage;
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        storageDao = new StorageDaoImpl(storage);
        operationHandler = new ReturnOperationHandler(storageDao);
    }

    @Test
    public void returnOperation_Ok() {
        storageDao.update("apple", 50);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 13);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get("apple");
        Integer expected = 63;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
