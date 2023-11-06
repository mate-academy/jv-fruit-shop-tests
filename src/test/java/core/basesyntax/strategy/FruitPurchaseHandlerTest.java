package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.strategy.impl.FruitPurchaseHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitPurchaseHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final String CORRECT_OPERATION_CODE = "p";
    private static final String INCORRECT_OPERATION_CODE = "a";
    private static final int DEFAULT_FRUIT_AMOUNT = 10;
    private static final int ADDITIONAL_FRUIT_AMOUNT = 5;
    private static final int ILLEGAL_FRUIT_AMOUNT = 11;
    private static FruitPurchaseHandler fruitPurchaseHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void setUp() {
        fruitPurchaseHandler = new FruitPurchaseHandler();
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setFruitDao() {
        fruitDao.addFirst(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
    }

    @Test
    void update_correctInputData_Ok() {
        fruitPurchaseHandler.updateStorage(DEFAULT_FRUIT_NAME, ADDITIONAL_FRUIT_AMOUNT);
        Integer fruitAmount = fruitDao.get(DEFAULT_FRUIT_NAME);
        Integer expected = ADDITIONAL_FRUIT_AMOUNT;
        assertEquals(expected, fruitAmount);
    }

    @Test
    void update_nullInputData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitPurchaseHandler.updateStorage(null, ADDITIONAL_FRUIT_AMOUNT));
    }

    @Test
    void update_incorrectInputData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitPurchaseHandler.updateStorage(DEFAULT_FRUIT_NAME, ILLEGAL_FRUIT_AMOUNT));
    }

    @Test
    void checkOperationCode_correctInputData_Ok() {
        assertTrue(fruitPurchaseHandler.isServiceApplicable(CORRECT_OPERATION_CODE));
    }

    @Test
    void checkOperationCode_incorrectInputData_notOk() {
        assertFalse(fruitPurchaseHandler.isServiceApplicable(INCORRECT_OPERATION_CODE));
    }

    @Test
    void checkOperationCode_nullInputData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitPurchaseHandler.isServiceApplicable(null));
    }

    @AfterEach
    void cleanFruitStorage() {
        fruitDao.removeAll();
    }
}
