package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandler(storageDao);
    }

    @Test
    public void balance_correct_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 40);
        operationHandler.doOperation(apple, 30);
        int expected = 30;
        int actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test
    public void balance_addNewFruit_Ok() {
        Fruit apple = new Fruit("apple");
        operationHandler.doOperation(apple, 30);
        int expected = 30;
        int actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
