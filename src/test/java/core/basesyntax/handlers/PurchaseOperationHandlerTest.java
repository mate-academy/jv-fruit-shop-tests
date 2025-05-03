package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String MANGO = "mango";
    private static final int VALID_PURCHASE_QUANTITY = 20;
    private static final int RESULT_QUANTITY = 10;
    private static final int VALID_QUANTITY = 30;
    private static final int INVALID_PURCHASE_QUANTITY = 200;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    void operate_ValidData_ok() {
        Storage.fruits.put(MANGO, VALID_QUANTITY);
        operationHandler.operate(MANGO, VALID_PURCHASE_QUANTITY);
        assertEquals(RESULT_QUANTITY, Storage.fruits.get(MANGO));
    }

    @Test
    void operate_InvalidData_notOk() {
        Storage.fruits.put(MANGO, VALID_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> operationHandler.operate(MANGO, INVALID_PURCHASE_QUANTITY),
                "Should be RuntimeException for purchase quantity "
                        + "that bigger than original quantity"
                        + INVALID_PURCHASE_QUANTITY);
    }

    @AfterAll
    static void tearDown() {
        Storage.fruits.clear();
    }
}
