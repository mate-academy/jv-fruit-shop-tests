package core.basesyntax.service.imp;

import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exeption.FruitShopExeption;
import core.basesyntax.service.FruitService;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceServiceTest {
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final String FRUIT_OK = "apple";
    private static final Integer AMOUNT_OK = 10;
    private static final Integer AMOUNT_0_NOTOK = 0;
    private static final Integer AMOUNT_LES_THEN_0_NOTOK = -5;
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitService = new BalanceService();
    }

    @Test(expected = FruitShopExeption.class)
    public void moveFruit_null_NotOk() {
        fruitService.moveFruit(null, AMOUNT_OK);
        fail("Expected " + FruitShopExeption.class.getName()
                + " to be thrown for not existing file, but it wasn't");
    }
}
