package handler;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static Storage storage;
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        storageDao = new StorageDaoImpl(storage);
        operationHandler = new SupplyOperationHandler(storageDao);
    }

    @Test
    public void supplyOperation_Ok() {
        storageDao.update("apple",20);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100);
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.storage.get("apple");
        Integer expected = 120;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}
