package core.basesyntax.service.operationhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QTY = 10;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        Storage.fruits.put(TEST_FRUIT, TEST_QTY);
    }

    @Test
    public void makeOperation_ok() {
        purchaseOperationHandler.makeOperation(TEST_FRUIT, 5);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(5));
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_toMuchPurchaseQty_notOk() {
        purchaseOperationHandler.makeOperation(TEST_FRUIT, 1000);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
