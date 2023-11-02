package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.strategy.impl.FruitBalanceHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitBalanceHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final String ADDITIONAL_FRUIT_NAME = "banana";
    private static final String CORRECT_OPERATION_CODE = "b";
    private static final String INCORRECT_OPERATION_CODE = "a";
    private static final int DEFAULT_FRUIT_AMOUNT = 10;
    private static final int ADDITIONAL_FRUIT_AMOUNT = 20;
    private static FruitBalanceHandler fruitBalanceHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    public static void setUp() {
        fruitBalanceHandler = new FruitBalanceHandler();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void update_correctInputDataCase1_Ok() {
        fruitBalanceHandler.updateStorage(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
        Integer fruitAmount = fruitDao.get(DEFAULT_FRUIT_NAME);
        Integer expected = DEFAULT_FRUIT_AMOUNT;
        Assertions.assertEquals(expected, fruitAmount);
    }

    @Test
    public void update_correctInputDataCase2_Ok() {
        String appleFruit = DEFAULT_FRUIT_NAME;
        String bananaFruit = ADDITIONAL_FRUIT_NAME;
        int appleBalance = DEFAULT_FRUIT_AMOUNT;
        int bananaBalance = ADDITIONAL_FRUIT_AMOUNT;
        fruitBalanceHandler.updateStorage(appleFruit, appleBalance);
        fruitBalanceHandler.updateStorage(bananaFruit, bananaBalance);
        Integer appleAmount = fruitDao.get(DEFAULT_FRUIT_NAME);
        Integer bananaAmount = fruitDao.get(ADDITIONAL_FRUIT_NAME);
        Assertions.assertEquals(appleAmount, appleBalance);
        Assertions.assertEquals(bananaAmount, bananaBalance);
    }

    @Test
    public void update_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitBalanceHandler.updateStorage(null, DEFAULT_FRUIT_AMOUNT));
    }

    @Test
    public void checkOperationCode_correctInputData_Ok() {
        assertTrue(fruitBalanceHandler.isServiceApplicable(CORRECT_OPERATION_CODE));
    }

    @Test
    public void checkOperationCode_incorrectInputData_notOk() {
        assertFalse(fruitBalanceHandler.isServiceApplicable(INCORRECT_OPERATION_CODE));
    }

    @Test
    public void checkOperationCode_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitBalanceHandler.isServiceApplicable(null));
    }

    @AfterEach
    public void cleanFruitStorage() {
        fruitDao.removeAll();
    }
}
