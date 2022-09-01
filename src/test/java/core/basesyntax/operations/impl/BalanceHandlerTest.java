package core.basesyntax.operations.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.operations.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String FRUIT_APPLE = "apple";
    private static FruitDao fruitDao;
    private OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void beforeEachTest() {
        Storage.fruits.put(FRUIT_APPLE, 100);
    }

    @Test
    public void balance_ok() {
        String newFruit = "apple";
        operationHandler = new BalanceHandler(fruitDao);
        int expected = 158;
        operationHandler.process(newFruit, expected);
        int actual = fruitDao.get(newFruit);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
