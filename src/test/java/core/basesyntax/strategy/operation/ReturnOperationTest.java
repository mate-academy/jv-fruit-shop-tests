package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final int AMOUNT_LESS_THAN_0 = -66;
    private static final int AMOUNT_LIMIT_ILLEGAL_VALUE = -1;
    private static final int AMOUNT_NORMAL_VALUE = 5;
    private static final int AMOUNT_ZERO = 0;
    private static final int NORMAL_QUANTITY = 16;
    private static final String NORMAL_VALUE = "apple";
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeEach
    void setUp() {
        fruitOperationHandler = new ReturnOperation(STORAGE_DAO);
        STORAGE_DAO.addFruit(NORMAL_VALUE, NORMAL_QUANTITY);
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

    @Test
    void applyOperation_AmountLessThanZero_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_LESS_THAN_0);
        });
    }

    @Test
    void applyOperation_LimitIllegalAmount_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_LIMIT_ILLEGAL_VALUE);
        });
    }
}
