package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static final String FRUIT_TYPE = "banana";
    private OperationHandler operationHandler;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandlerImpl(storageDao);
    }

    @Test
    public void balanceOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_TYPE,14);
        operationHandler.changeQuantity(fruitTransaction);
        Integer expected = 14;
        Integer actual = storageDao.getFruitBalance(FRUIT_TYPE);
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}
