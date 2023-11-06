package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String MANGO = "mango";
    private static final int VALID_QUANTITY = 100;
    private static final int INVALID_QUANTITY = -30;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    void operate_ValidData_ok() {
        operationHandler.operate(MANGO, VALID_QUANTITY);
        assertEquals(100, Storage.fruits.get(MANGO));
    }

    @Test
    void operate_InvalidData_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.operate(MANGO, INVALID_QUANTITY),
                "Should be RuntimeException for negative quantity");
    }

    @AfterAll
    static void tearDown() {
        Storage.fruits.clear();
    }
}
