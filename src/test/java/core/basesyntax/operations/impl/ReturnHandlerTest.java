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

public class ReturnHandlerTest {
    private static final String FRUIT_APPLE = "apple";
    private static FruitDao fruitsDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        fruitsDao = new FruitDaoImpl();
        operationHandler = new ReturnHandler(fruitsDao);
    }

    @Before
    public void beforeEachTest() {
        Storage.fruits.put(FRUIT_APPLE, 100);
    }

    @Test
    public void return_ok() {
        operationHandler.process(FRUIT_APPLE, 20);
        int expected = 120;
        int actual = Storage.fruits.get(FRUIT_APPLE);
        assertEquals("After return 20 apples should be 120 for beginning balance 100",
                expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
