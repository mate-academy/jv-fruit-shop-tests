package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final int VALID_QUANTITY = 100;
    private static final int INVALID_SUPPLY_QUANTITY = -30;
    private static final int RESULT_QUANTITY = 200;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    void operate_ValidData_ok() {
        Storage.fruits.put(BANANA,VALID_QUANTITY);
        operationHandler.operate(BANANA, VALID_QUANTITY);
        assertEquals(RESULT_QUANTITY, Storage.fruits.get(BANANA));
    }

    @Test
    void operate_InvalidData_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.operate(BANANA, INVALID_SUPPLY_QUANTITY),
                "Should be RuntimeException for negative quantity" + INVALID_SUPPLY_QUANTITY);
    }

    @AfterAll
    static void tearDown() {
        Storage.fruits.clear();
    }
}
