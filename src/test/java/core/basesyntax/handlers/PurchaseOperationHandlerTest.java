package core.basesyntax.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @Before
    public void setUp() {
        operationHandler = new PurchaseOperationHandler(new StorageDaoImpl());
    }

    @Test
    public void purchase_correct_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 40);
        operationHandler.doOperation(apple, 30);
        int expected = 10;
        int actual = Storage.storage.get(apple);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_wrongInput_NotOk() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 10);
        operationHandler.doOperation(apple, 40);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
