package core.basesyntax.service.operationhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QTY = 10;
    private StorageDao storageDao = new StorageDaoImpl();
    private SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler(storageDao);

    @Test
    public void makeOperation_ok() {
        Storage.fruits.put(TEST_FRUIT, TEST_QTY);
        supplyOperationHandler.makeOperation(TEST_FRUIT, 5);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(15));
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_negativeQty_notOk() {
        supplyOperationHandler.makeOperation(TEST_FRUIT, -5);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
