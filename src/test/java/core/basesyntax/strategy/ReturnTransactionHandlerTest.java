package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.models.Fruit;
import core.basesyntax.models.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnTransactionHandlerTest {
    private static TransactionHandler handler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        handler = new ReturnTransactionHandler(storageDao);
    }

    @Test
    public void handleTransaction_ReturnProper_Ok() {
        Fruit fruit = new Fruit("Apple");
        Storage.storage.put(fruit, 20);
        Transaction transaction = new Transaction("r", fruit, 10);
        handler.handleTransaction(transaction);
        int expected = 30;
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleTransaction_NonExistingFruit_NotOk() {
        Fruit notAFruit = new Fruit("It`s not a fruit");
        Transaction transactionNonExisting = new Transaction("r", notAFruit, 10);
        handler.handleTransaction(transactionNonExisting);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
