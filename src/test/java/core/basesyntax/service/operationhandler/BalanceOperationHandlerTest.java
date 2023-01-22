package core.basesyntax.service.operationhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_QTY = 10;
    private BalanceOperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
    }

    @Test
    public void makeOperation_ok() {
        balanceOperationHandler.makeOperation(TEST_FRUIT, TEST_QTY);
        assertEquals(1, Storage.fruits.size());
        assertTrue(Storage.fruits.containsValue(TEST_QTY));
        assertTrue(Storage.fruits.containsKey(TEST_FRUIT));
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_negativeQty_notOk() {
        balanceOperationHandler.makeOperation(TEST_FRUIT, -5);
    }
}
