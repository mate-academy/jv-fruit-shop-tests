package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = 5;
        assertEquals(firstExpectedQuantity, operationHandler.countQuantity(20, 15));
        int secondExpectedQuantity = 87;
        assertEquals(secondExpectedQuantity, operationHandler.countQuantity(100, 13));
    }

    @Test
    void countQuantity_negativeOperationAmount_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(10, -15));
        String expected = "Operation amount can not be less than 0";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void countQuantity_negativeCurrentAmount_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(-15, 11));
        String expected = "Current amount can not be less than 0";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void countQuantity_negativeResult_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(10, 11));
        String expected = "Balance can not be less than 0";
        assertEquals(expected, exception.getMessage());
    }
}
