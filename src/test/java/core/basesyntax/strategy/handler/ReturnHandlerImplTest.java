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

public class ReturnHandlerImplTest {
    private static StorageDao storageDao;
    private static TransactionHandler returnHandler;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        returnHandler = new ReturnHandlerImpl(storageDao);
    }

    @Before
    public void setUp() {
        Storage.storage.put(new Fruit("apple"), 100);
        Storage.storage.put(new Fruit("orange"), 100);
    }

    @Test(expected = RuntimeException.class)
    public void makeTransaction_nonExistentFruit_NotOk() {
        returnHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20));
    }

    @Test
    public void makeTransaction_existentFruit_Ok() {
        Integer expected = 200;
        returnHandler.makeTransaction(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 100));
        Integer actual = Storage.storage.get(new Fruit("orange"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
