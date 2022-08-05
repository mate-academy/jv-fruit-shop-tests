package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.operations.impl.BalanceHandler;
import core.basesyntax.operations.impl.PurchaseHandler;
import core.basesyntax.operations.impl.ReturnHandler;
import core.basesyntax.operations.impl.SupplyHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlersTest {
    private static final String FRUIT_BANANA = "banana";
    private FruitsDao fruitsDao;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        fruitsDao = new FruitsDaoImpl();
        Storage.fruits.put(FRUIT_BANANA, 100);
    }

    @Test
    public void balance_ok() {
        String newFruit = "apple";
        operationHandler = new BalanceHandler(fruitsDao);
        int expected = 158;
        operationHandler.process(newFruit, expected);
        int actual = fruitsDao.getAmount(newFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void purchase_ok() {
        operationHandler = new PurchaseHandler(fruitsDao);
        operationHandler.process(FRUIT_BANANA, 30);
        int actual = fruitsDao.getAmount(FRUIT_BANANA);
        int expected = 70;
        assertEquals("After purchase 30 bananas should leave 70 for beginning balance 100",
                expected, actual);
    }

    @Test
    public void return_ok() {
        operationHandler = new ReturnHandler(fruitsDao);
        operationHandler.process(FRUIT_BANANA, 20);
        int expected = 120;
        int actual = fruitsDao.getAmount(FRUIT_BANANA);
        assertEquals("After return 20 fruits should be 120 for beginning balance 100",
                expected, actual);
    }

    @Test
    public void supply_ok() {
        operationHandler = new SupplyHandler(fruitsDao);
        operationHandler.process(FRUIT_BANANA, 50);
        int expected = 150;
        int actual = fruitsDao.getAmount(FRUIT_BANANA);
        assertEquals("After supply 50 fruits should be 150 for beginning balance 100",
                expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
