package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final int AMOUNT_LESS_THAN_ZERO = -66;
    private static final int AMOUNT_LIMIT_ILLEGAL_VALUE = -1;
    private static final int AMOUNT_NORMAL_VALUE = 7;
    private static final int AMOUNT_ZERO = 0;
    private static final int NORMAL_QUANTITY = 16;
    private static final String NORMAL_VALUE = "apple";
    private static final String NEW_FRUIT_NAME = "orange";
    private static final int NEW_QUANTITY = 11;
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeEach
    void setUp() {
        fruitOperationHandler = new SupplyOperation(STORAGE_DAO);
        STORAGE_DAO.addFruit(NORMAL_VALUE, NORMAL_QUANTITY);
    }

    @Test
    void applyOperation_NewFruit_Ok() {
        fruitOperationHandler.applyOperation(NEW_FRUIT_NAME, NEW_QUANTITY);
        String actual = STORAGE_DAO.getFruit(NEW_FRUIT_NAME).getFruitName();
        String expected = NEW_FRUIT_NAME;
        assertEquals(expected, actual);
    }

    @Test
    void applyOperation_AmountLessThanZero_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_LESS_THAN_ZERO);
        });
    }

    @Test
    void applyOperation_LimitIllegalAmount_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_LIMIT_ILLEGAL_VALUE);
        });
    }

    @Test
    void applyOperation_AmountNormalValue_Ok() {
        fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_NORMAL_VALUE);
        int actual = STORAGE_DAO.getFruit(NORMAL_VALUE).getQuantity();
        int expected = NORMAL_QUANTITY + AMOUNT_NORMAL_VALUE;
        assertEquals(expected, actual);
    }

    @Test
    void applyOperation_AmountZero_Ok() {
        fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_ZERO);
        int actual = STORAGE_DAO.getFruit(NORMAL_VALUE).getQuantity();
        int expected = NORMAL_QUANTITY + AMOUNT_ZERO;
        assertEquals(expected, actual);
    }
}
