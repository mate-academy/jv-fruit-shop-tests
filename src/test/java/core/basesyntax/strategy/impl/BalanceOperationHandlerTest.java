package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static FruitStorageDao fruitStorageDao;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
        handler = new BalanceOperationHandler(fruitStorageDao);
    }

    @Before
    public void beforeEach() {
        fruitStorageDao.getAll().clear();
    }

    @Test
    public void balanceHandler_updateValidBalance_ok() {
        handler.handleOperation("apple",10);
        int actual = fruitStorageDao.getQuantity(new Fruit("apple"));
        int expected = 10;
        Assert.assertEquals(expected, actual);
    }
}
