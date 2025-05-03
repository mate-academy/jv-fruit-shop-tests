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

public class BalanceHandlerImplTest {
    private static StorageDao storageDao;
    private static TransactionHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        balanceHandler = new BalanceHandlerImpl(storageDao);
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("banana"), 100);
        Storage.storage.put(new Fruit("orange"), 100);
    }

    @Test
    public void makeTransaction_ExistentFruit_Ok() {
        Integer expected = 20;
        balanceHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 20));
        Integer actual = Storage.storage.get(new Fruit("orange"));
        assertEquals(expected, actual);
    }

    @Test
    public void makeTransaction_nonExistentFruit_Ok() {
        Integer expected = 20;
        balanceHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
