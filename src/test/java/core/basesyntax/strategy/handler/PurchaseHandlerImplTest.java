package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerImplTest {
    private static Handler purchaseHandler;
    private static Storage storage;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandlerImpl();
    }

    @After
    public void tearDown() {
        storage.storageDB.clear();
    }

    @Test(expected = RuntimeException.class)
    public void handler_addNull_not_ok() {
        purchaseHandler.handler(null);
    }

    @Test(expected = RuntimeException.class)
    public void handler_addNegativeQuantity() {
        purchaseHandler.handler(new FruitTransaction("p","test",-34));
    }

    @Test
    public void handler_subtractionCheck_ok() {
        storage.storageDB.put("test1",20);
        storage.storageDB.put("test2",50);
        storage.storageDB.put("test3",10);
        purchaseHandler.handler(new FruitTransaction("p","test1",10));
        purchaseHandler.handler(new FruitTransaction("p","test2",10));
        purchaseHandler.handler(new FruitTransaction("p","test3",10));
        int expect1 = storage.storageDB.get("test1");
        int expect2 = storage.storageDB.get("test2");
        int expect3 = storage.storageDB.get("test3");
        assertEquals(expect1,10);
        assertEquals(expect2,40);
        assertEquals(expect3,0);
    }
}
