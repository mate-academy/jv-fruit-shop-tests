package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.strategy.impl.FruitSupplyHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitSupplyHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final String CORRECT_OPERATION_CODE = "s";
    private static final String INCORRECT_OPERATION_CODE = "a";
    private static final int DEFAULT_FRUIT_AMOUNT = 10;
    private static final int FRUIT_AMOUNT = 20;
    private static final int ADDITIONAL_FRUIT_AMOUNT = 30;
    private static FruitSupplyHandler fruitSupplyHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    public static void setUp() {
        fruitSupplyHandler = new FruitSupplyHandler();
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    public void setFruitDao() {
        fruitDao.addFirst(DEFAULT_FRUIT_NAME, FRUIT_AMOUNT);
    }

    @Test
    public void update_correctInputData_Ok() {
        fruitSupplyHandler.updateStorage(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
        Integer fruitAmount = fruitDao.get(DEFAULT_FRUIT_NAME);
        Integer expected = ADDITIONAL_FRUIT_AMOUNT;
        Assertions.assertEquals(expected, fruitAmount);
    }

    @Test
    public void update_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitSupplyHandler.updateStorage(null, DEFAULT_FRUIT_AMOUNT));
    }

    @Test
    public void checkOperationCode_correctInputData_Ok() {
        assertTrue(fruitSupplyHandler.isServiceApplicable(CORRECT_OPERATION_CODE));
    }

    @Test
    public void checkOperationCode_incorrectInputData_notOk() {
        assertFalse(fruitSupplyHandler.isServiceApplicable(INCORRECT_OPERATION_CODE));
    }

    @Test
    public void checkOperationCode_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitSupplyHandler.isServiceApplicable(null));
    }

    @AfterEach
    public void cleanFruitStorage() {
        fruitDao.removeAll();
    }
}
