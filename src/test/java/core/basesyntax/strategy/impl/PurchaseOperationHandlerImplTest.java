package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static final String FRUIT_TYPE = "banana";
    private OperationHandler operationHandler;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandlerImpl(storageDao);
        storageDao.getStorage().put(FRUIT_TYPE,15);
    }

    @Test
    public void purchase_Operation_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT_TYPE,14);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = 1;
        Integer actual = storageDao.getFruitBalance(FRUIT_TYPE);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_Operation_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT_TYPE,16);
        operationHandler.changeQuantity(fruitTransaction);
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}
