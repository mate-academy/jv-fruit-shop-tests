package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static TransactionHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        purchaseHandler = new PurchaseHandlerImpl(storageDao);
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("orange"), 100);
    }

    @Test (expected = RuntimeException.class)
    public void makeTransaction_nonExistentFruit_NotOk() {
        purchaseHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20));
    }

    @Test
    public void makeTransaction_existentFruit_Ok() {
        Integer expected = 0;
        purchaseHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 100));
        Integer actual = Storage.storage.get(new Fruit("orange"));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void makeTransaction_transactionAmountBiggerThenFruit_NotOk() {
        purchaseHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 101));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
