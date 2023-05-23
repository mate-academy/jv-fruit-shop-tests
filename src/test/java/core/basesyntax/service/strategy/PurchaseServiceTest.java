package core.basesyntax.service.strategy;

import static org.junit.Assert.fail;

import core.basesyntax.exeption.FruitShopExeption;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseServiceTest {
    private static final String FRUIT_OK = "apple";
    private static final Integer AMOUNT_OK = 10;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseService();
    }

    @Test(expected = FruitShopExeption.class)
    public void moveFruit_nullFruit_NotOk() {
        operationHandler.moveFruit(null, AMOUNT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void moveAmount_nullAmount_NotOk() {
        operationHandler.moveFruit(FRUIT_OK, null);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }
}
