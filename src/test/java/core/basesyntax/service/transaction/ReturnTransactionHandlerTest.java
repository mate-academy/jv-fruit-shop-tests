package core.basesyntax.service.transaction;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnTransactionHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static TransactionHandler handler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        handler = new ReturnTransactionHandler(storageDao);
    }

    @Test
    public void handle_successHandleNotEmptyStorage_ok() {
        Storage.fruitStorage.put(FRUIT_NAME, 100);
        Integer expected = 200;
        Transaction transaction = new Transaction(Operation.SUPPLY, FRUIT_NAME, 100);
        handler.handle(transaction);
        Integer actual = Storage.fruitStorage.get("banana");
        Assert.assertEquals("Test failed! Expected " + expected + " units "
                + " but are " + actual + " units", expected, actual);
    }

    @Test
    public void handle_successReturnEmptyStorage_ok() {
        int expected = 20;
        Transaction transaction = new Transaction(Operation.RETURN, FRUIT_NAME, 20);
        handler.handle(transaction);
        Assert.assertTrue("Test failed! The Storage must contain " + FRUIT_NAME,
                Storage.fruitStorage.containsKey(FRUIT_NAME));
        int actual = Storage.fruitStorage.get(FRUIT_NAME);
        Assert.assertEquals("Test failed! Expected " + expected + " units "
                + " but are " + actual + " units", expected, actual);
    }

    @After
    public void tearDown(){
        Storage.fruitStorage.clear();
    }
}