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

public class SupplyHandlerImplTest {
    private static StorageDao storageDao;
    private static TransactionHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        supplyHandler = new SupplyHandlerImpl(storageDao);
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("orange"), 100);
    }

    @Test(expected = RuntimeException.class)
    public void makeTransaction_nonExistentFruit_NotOk() {
        supplyHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20));
    }

    @Test
    public void makeTransaction_existentFruit_Ok() {
        Integer expected = 200;
        supplyHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 100));
        Integer actual = Storage.storage.get(new Fruit("orange"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
