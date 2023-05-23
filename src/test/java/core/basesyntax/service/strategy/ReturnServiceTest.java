package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.Storage;
import core.basesyntax.exeption.FruitShopExeption;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnServiceTest {
    private static final Integer AMOUNT_OK = 10;
    private static final String FRUIT_OK = "apple";
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnService();
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
        assertEquals("Expected amount of fruit = " + expected + ", but was " + actual,
                actual, expected);
    }
}
