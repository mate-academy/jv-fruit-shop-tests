package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;
    private static FruitStorageDao fruitStorageDao;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @Before
    public void beforeEach() {
        fruitStorageDao.getAll().clear();
        fruitStorageDao.add(new Fruit("apple"), 20);
        handler = new PurchaseOperationHandler(fruitStorageDao);
    }

    @Test
    public void handlePurchaseOperation_validWork_ok() {
        handler.handleOperation("apple",20);
        int expected = 0;
        int actual = fruitStorageDao.getQuantity(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handlePurchaseOperation_notEnoughQuantity_notOk() throws RuntimeException {
        handler.handleOperation("apple",21);
    }
}
