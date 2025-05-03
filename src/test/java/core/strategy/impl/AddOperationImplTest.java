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

public class AddOperationImplTest {
    private static OperationHandler addActivityHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        addActivityHandler = new AddOperationImpl(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana", 20));
    }

    @Test
    public void apply_correctWork_ok() {
        int expected = 34;
        addActivityHandler.apply("banana", 14);
        int actual = fruitDao.get("banana").getQuantity();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
