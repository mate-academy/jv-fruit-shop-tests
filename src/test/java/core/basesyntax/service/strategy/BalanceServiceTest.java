package core.basesyntax.service.imp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import core.basesyntax.dao.Storage;
import core.basesyntax.exeption.FruitShopExeption;
import core.basesyntax.service.strategy.BalanceService;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceServiceTest {
    private static final String FRUIT_OK = "apple";
    private static final Integer AMOUNT_OK = 10;
    private static final Integer AMOUNT_0_NOTOK = 0;
    private static final Integer AMOUNT_LES_THEN_0_NOTOK = -5;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new BalanceService();
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test(expected = FruitShopExeption.class)
    public void moveFruit_null_NotOk() {
        operationHandler.moveFruit(null, AMOUNT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test
    public void moveFruit_Ok() {
        Storage.fruits.clear();
        operationHandler.moveFruit(FRUIT_OK, AMOUNT_OK);
        Integer expected = AMOUNT_OK;
        Integer actual = Storage.get(FRUIT_OK);
        assertTrue("Expected amount of fruit = " + expected + ", but was " + actual,
                actual.equals(expected));
    }
}
