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

public class PurchaseTransactionHandlerTest {
    private static TransactionHandler handler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        handler = new PurchaseTransactionHandler(storageDao);
    }

    @Test
    public void handleTransaction_purchaseProper_Ok() {
        Fruit fruit = new Fruit("Apple");
        Storage.storage.put(fruit, 20);
        Transaction transaction = new Transaction("p", fruit, 10);
        handler.handleTransaction(transaction);
        int expected = 10;
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleTransaction_nonExistingFruit_NotOk() {
        Fruit notAFruit = new Fruit("It`s not a fruit");
        Transaction transactionNonExisting = new Transaction("p", notAFruit, 10);
        handler.handleTransaction(transactionNonExisting);
    }

    @Test(expected = RuntimeException.class)
    public void handleTransaction_overbuy_NotOk() {
        Fruit fruit = new Fruit("Banana");
        Storage.storage.put(fruit, 100);
        Transaction transactionNonExisting = new Transaction("p", fruit, 101);
        handler.handleTransaction(transactionNonExisting);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
