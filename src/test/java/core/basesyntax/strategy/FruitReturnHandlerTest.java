package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.strategy.impl.FruitReturnHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitReturnHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final String CORRECT_OPERATION_CODE = "r";
    private static final String INCORRECT_OPERATION_CODE = "a";
    private static final int DEFAULT_FRUIT_AMOUNT = 10;
    private static final int FRUIT_AMOUNT = 20;
    private static final int ADDITIONAL_FRUIT_AMOUNT = 30;
    private static FruitReturnHandler fruitReturnHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    public static void setUp() {
        fruitReturnHandler = new FruitReturnHandler();
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    public void setFruitDao() {
        fruitDao.addFirst(DEFAULT_FRUIT_NAME, FRUIT_AMOUNT);
    }

    @Test
    public void update_correctInputData_Ok() {
        fruitReturnHandler.updateStorage(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
        Integer fruitAmount = fruitDao.get(DEFAULT_FRUIT_NAME);
        Integer expected = ADDITIONAL_FRUIT_AMOUNT;
        Assertions.assertEquals(expected, fruitAmount);
    }

    @Test
    public void update_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitReturnHandler.updateStorage(null, DEFAULT_FRUIT_AMOUNT));
    }

    @Test
    public void checkOperationCode_correctInputData_Ok() {
        assertTrue(fruitReturnHandler.isServiceApplicable(CORRECT_OPERATION_CODE));
    }

    @Test
    public void checkOperationCode_incorrectInputData_notOk() {
        assertFalse(fruitReturnHandler.isServiceApplicable(INCORRECT_OPERATION_CODE));
    }

    @Test
    public void checkOperationCode_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitReturnHandler.isServiceApplicable(null));
    }

    @AfterEach
    public void cleanFruitStorage() {
        fruitDao.removeAll();
    }
}
