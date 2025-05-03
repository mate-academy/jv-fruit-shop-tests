package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static final String FRUIT_TYPE = "banana";
    private static final String FRUIT_TYPE_INVALID = "apple";
    private OperationHandler operationHandler;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperationHandlerImpl(storageDao);
        storageDao.getStorage().put(FRUIT_TYPE,100);
    }

    @Test
    public void return_operation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_TYPE,15);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = 115;
        Integer actual = storageDao.getFruitBalance(FRUIT_TYPE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void returnOperation_invalidFruit_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_TYPE_INVALID,15);
        operationHandler.changeQuantity(fruitTransaction);
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}
