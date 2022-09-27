package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("p"));
        fruitTransaction.setFruit("banana");
        storageDao.update("banana",20);
    }

    @Test
    public void purchasePositive_Ok() {
        Integer expected = 10;
        fruitTransaction.setQuantity(10);
        purchaseOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test
    public void purchaseZero_Ok() {
        Integer expected = 20;
        fruitTransaction.setQuantity(0);
        purchaseOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test
    public void purchaseNegative_NotOk() {
        Integer expected = 45;
        fruitTransaction.setQuantity(-25);
        purchaseOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        purchaseOperationHandler.getOperation(fruitTransaction);
    }

    @Test
    public void purchaseEmptyValue_Ok() {
        Integer expected = 20;
        purchaseOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @After
    public void tearDown() throws Exception {
        storageDao.getAllFruitsFromStorage().clear();
    }
}
