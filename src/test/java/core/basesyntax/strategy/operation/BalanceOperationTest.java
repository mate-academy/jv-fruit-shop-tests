package core.basesyntax.strategy.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final int QUANTITY_LESS_THAN_ZERO = -66;
    private static final int QUANTITY_LIMIT_ILLEGAL_VALUE = -1;
    private static final int QUANTITY_ZERO = 0;
    private static final int NORMAL_QUANTITY = 16;
    private static final String NORMAL_VALUE = "apple";
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeEach
    void setUp() {
        fruitOperationHandler = new BalanceOperation(STORAGE_DAO);
    }

    @Test
    void applyOperation_QuantityLessThan0_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, QUANTITY_LESS_THAN_ZERO);
        });
    }

    @Test
    void applyOperation_LimitNotOkValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitOperationHandler.applyOperation(NORMAL_VALUE, QUANTITY_LIMIT_ILLEGAL_VALUE);
        });
    }

    @Test
    void applyOperation_QuantityZero_Ok() {
        fruitOperationHandler.applyOperation(NORMAL_VALUE, QUANTITY_ZERO);
        int actual = STORAGE_DAO.getFruit(NORMAL_VALUE).getQuantity();
        int expected = QUANTITY_ZERO;
        assertEquals(expected, actual);
    }

    @Test
    void applyOperation_QuantityAndNameOk_Ok() {
        fruitOperationHandler.applyOperation(NORMAL_VALUE, NORMAL_QUANTITY);
        int actual = STORAGE_DAO.getFruit(NORMAL_VALUE).getQuantity();
        int expected = NORMAL_QUANTITY;
        assertEquals(expected, actual);
    }
}
