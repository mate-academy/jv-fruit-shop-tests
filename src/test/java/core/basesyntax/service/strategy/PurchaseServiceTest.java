package core.basesyntax.service.imp;

import static org.junit.Assert.fail;

import core.basesyntax.exeption.FruitShopExeption;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseService;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseServiceTest {
    //private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final String FRUIT_OK = "apple";
    private static final Integer AMOUNT_OK = 10;
    private static final Integer AMOUNT_0_NOTOK = 0;
    private static final Integer AMOUNT_LES_THEN_0_NOTOK = -5;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new PurchaseService();
    }

    @Test(expected = FruitShopExeption.class)
    public void moveFruit_null_NotOk() {
        operationHandler.moveFruit(null, AMOUNT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }

    @Test(expected = FruitShopExeption.class)
    public void moveAmount_null_NotOk() {
        operationHandler.moveFruit(FRUIT_OK, null);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }
}
