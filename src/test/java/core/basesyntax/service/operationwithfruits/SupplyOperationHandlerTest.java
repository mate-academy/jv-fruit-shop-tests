package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("s"));
        fruitTransaction.setFruit("banana");
        storageDao.update("banana",10);
    }

    @Test
    public void supplyPositive_Ok() {
        Integer expected = 20;
        fruitTransaction.setQuantity(10);
        supplyOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test
    public void supplyZero_Ok() {
        Integer expected = 10;
        fruitTransaction.setQuantity(0);
        supplyOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test (expected = RuntimeException.class)
    public void supplyNegative_NotOk() {
        fruitTransaction.setQuantity(-15);
        supplyOperationHandler.getOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void supplyMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        supplyOperationHandler.getOperation(fruitTransaction);
    }

    @Test
    public void supplyEmptyValue_Ok() {
        Integer expected = 10;
        supplyOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @After
    public void tearDown() {
        storageDao.getAllFruitsFromStorage().clear();
    }
}
