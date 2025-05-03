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

public class SupplyTransactionHandlerTest {
    private static TransactionHandler handler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        handler = new SupplyTransactionHandler(storageDao);
    }

    @Test
    public void handleTransaction_returnProper_Ok() {
        Fruit fruit = new Fruit("Apple");
        Storage.storage.put(fruit, 20);
        Transaction transaction = new Transaction("r", fruit, 20);
        handler.handleTransaction(transaction);
        int expected = 40;
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
