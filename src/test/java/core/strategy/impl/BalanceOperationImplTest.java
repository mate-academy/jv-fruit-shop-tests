package core.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.dao.FruitDao;
import core.dao.FruitDaoImpl;
import core.db.Storage;
import core.model.Fruit;
import core.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationImplTest {
    private static OperationHandler operationHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperationImpl(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana", 40));
    }

    @Test
    public void apply_correctWork_ok() {
        int expected = 40;
        operationHandler.apply("banana", 30);
        int actual = fruitDao.get("banana").getQuantity();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
