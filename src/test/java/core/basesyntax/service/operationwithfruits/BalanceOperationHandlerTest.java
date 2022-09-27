package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private BalanceOperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("b"));
        fruitTransaction.setFruit("banana");
    }

    @Test
    public void balanceSetPositive_Ok() {
        fruitTransaction.setQuantity(10);
        balanceOperationHandler.getOperation(fruitTransaction);
        assertEquals(1,storageDao.getAllFruitsFromStorage().size());
        assertTrue(storageDao.getAllFruitsFromStorage()
                .containsKey(fruitTransaction.getFruit()));
        assertTrue(storageDao.getAllFruitsFromStorage()
                .containsValue(fruitTransaction.getQuantity()));
    }

    @Test
    public void balanceSetZero_Ok() {
        fruitTransaction.setQuantity(0);
        balanceOperationHandler.getOperation(fruitTransaction);
        assertEquals(1,storageDao.getAllFruitsFromStorage().size());
        assertTrue(storageDao.getAllFruitsFromStorage()
                .containsValue(fruitTransaction.getQuantity()));
    }

    @Test (expected = RuntimeException.class)
    public void balanceSetNegative_NotOk() {
        fruitTransaction.setQuantity(-10);
        balanceOperationHandler.getOperation(fruitTransaction);
    }

    @Test
    public void balanceSetMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        balanceOperationHandler.getOperation(fruitTransaction);
        assertEquals(1,storageDao.getAllFruitsFromStorage().size());
        assertTrue(storageDao.getAllFruitsFromStorage()
                .containsValue(fruitTransaction.getQuantity()));
    }

    @Test
    public void balanceSetEmptyValue_Ok() {
        balanceOperationHandler.getOperation(fruitTransaction);
        assertEquals(1,storageDao.getAllFruitsFromStorage().size());
        assertTrue(storageDao.getAllFruitsFromStorage()
                .containsValue(fruitTransaction.getQuantity()));
    }

    @After
    public void tearDown() throws Exception {
        storageDao.getAllFruitsFromStorage().clear();
    }
}
