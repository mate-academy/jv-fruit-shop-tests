package core.basesyntax.service.operationhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QTY = 10;
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() throws Exception {
        StorageDao storageDao = new StorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
    }

    @Test
    public void makeOperation_ok() {
        Storage.fruits.put(TEST_FRUIT, TEST_QTY);
        returnOperationHandler.makeOperation(TEST_FRUIT, 5);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
        assertTrue(Storage.fruits.containsValue(15));
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_negativeQty_notOk() {
        returnOperationHandler.makeOperation(TEST_FRUIT, -5);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
