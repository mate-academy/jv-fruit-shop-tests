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

public class SupplyHandlerTest {
    private static final String FRUIT_APPLE = "apple";
    private static FruitDao fruitsDao;
    private OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        fruitsDao = new FruitDaoImpl();
    }

    @Before
    public void beforeEachTest() {
        Storage.fruits.put(FRUIT_APPLE, 100);
    }

    @Test
    public void supply_ok() {
        operationHandler = new SupplyHandler(fruitsDao);
        operationHandler.process(FRUIT_APPLE, 50);
        int expected = 150;
        int actual = fruitsDao.get(FRUIT_APPLE);
        assertEquals("After supply 50 apples should be 150 for beginning balance 100",
                expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
