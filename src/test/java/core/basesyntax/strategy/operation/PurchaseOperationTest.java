package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final int AMOUNT_LESS_THAN_ZERO = -66;
    private static final int AMOUNT_LIMIT_ILLEGAL_VALUE = -1;
    private static final int AMOUNT_GREATER_THAN_QUANTITY = 19;
    private static final int AMOUNT_ZERO = 0;
    private static final int NORMAL_QUANTITY = 16;
    private static final String NORMAL_VALUE = "apple";
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeEach
    void setUp() {
        fruitOperationHandler = new PurchaseOperation(STORAGE_DAO);
        STORAGE_DAO.addFruit(NORMAL_VALUE, NORMAL_QUANTITY);
    }

    @Test
    void applyOperation_AmountGreaterQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_GREATER_THAN_QUANTITY);
        });
    }

    @Test
    void applyOperation_Amount0_Ok() {
        fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_ZERO);
        int actual = STORAGE_DAO.getFruit(NORMAL_VALUE).getQuantity();
        int expected = NORMAL_QUANTITY + AMOUNT_ZERO;
        assertEquals(expected,actual);
    }

    @Test
    void applyOperation_AmountLessThan0_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_LESS_THAN_ZERO);
        });
    }

    @Test
    void applyOperation_LimitIllegalValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, AMOUNT_LIMIT_ILLEGAL_VALUE);
        });
    }
}
