package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = 15;
        assertEquals(firstExpectedQuantity, operationHandler.countQuantity(0, 15));
        int secondExpectedQuantity = 113;
        assertEquals(secondExpectedQuantity, operationHandler.countQuantity(100, 13));
    }

    @Test
    void countQuantity_negativeOperationAmount_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(10, -15));
        assertEquals("Operation amount can not be less than 0", exception.getMessage());
    }

    @Test
    void countQuantity_negativeCurrentAmount() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(-15, 11));
        assertEquals("Current amount can not be less than 0", exception.getMessage());
    }
}
