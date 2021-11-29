package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler handler;
    private static FruitStorageDao fruitStorageDao;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
        handler = new ReturnOperationHandler(fruitStorageDao);
    }

    @Before
    public void beforeEach() {
        fruitStorageDao.getAll().clear();
        fruitStorageDao.add(new Fruit("apple"), 20);
    }

    @Test
    public void handleReturnOperation_validWork_ok() {
        handler.handleOperation("apple", 20);
        int expected = 40;
        int actual = fruitStorageDao.getQuantity(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handleReturnOperation_noSuchFruit_ok() throws RuntimeException {
        handler.handleOperation("not and apple", 20);
    }
}
