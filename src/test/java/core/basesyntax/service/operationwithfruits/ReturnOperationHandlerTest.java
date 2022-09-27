package core.basesyntax.service.operationwithfruits;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(fruitTransaction.convertOperation("r"));
        fruitTransaction.setFruit("banana");
        storageDao.update("banana",10);
    }

    @Test
    public void returnPositive_Ok() {
        Integer expected = 20;
        fruitTransaction.setQuantity(10);
        returnOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test
    public void returnZero_Ok() {
        Integer expected = 10;
        fruitTransaction.setQuantity(0);
        returnOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @Test (expected = RuntimeException.class)
    public void returnNegative_NotOk() {
        fruitTransaction.setQuantity(-15);
        returnOperationHandler.getOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void returnMaxValue_Ok() {
        fruitTransaction.setQuantity(Integer.MAX_VALUE);
        returnOperationHandler.getOperation(fruitTransaction);
    }

    @Test
    public void returnEmptyValue_Ok() {
        Integer expected = 10;
        returnOperationHandler.getOperation(fruitTransaction);
        Integer count = storageDao.getCountFruit(fruitTransaction.getFruit());
        assertEquals(expected,count);
    }

    @After
    public void tearDown() throws Exception {
        storageDao.getAllFruitsFromStorage().clear();
    }
}
