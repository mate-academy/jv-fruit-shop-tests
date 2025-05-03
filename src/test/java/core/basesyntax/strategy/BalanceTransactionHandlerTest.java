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

public class BalanceTransactionHandlerTest {
    private static TransactionHandler handler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        handler = new BalanceTransactionHandler(storageDao);
    }

    @Test
    public void handleTransaction_balanceProper_Ok() {
        Fruit fruit = new Fruit("Apple");
        Transaction transaction = new Transaction("b", fruit, 10);
        handler.handleTransaction(transaction);
        int expected = 10;
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
