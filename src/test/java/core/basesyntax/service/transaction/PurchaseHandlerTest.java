package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final int INITIAL_AMOUNT = 10;
    private static final int FIRST_INITIAL_AMOUNT = 15;
    private static final int FIRST_OPERATION_AMOUNT = 10;
    private static final int SECOND_INITIAL_AMOUNT = 100;
    private static final int SECOND_OPERATION_AMOUNT = 13;
    private static final int NEGATIVE_OPERATION_AMOUNT = -15;
    private static final int CURRENT_AMOUNT = 11;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = 5;
        assertEquals(firstExpectedQuantity, operationHandler
                .countQuantity(FIRST_INITIAL_AMOUNT, FIRST_OPERATION_AMOUNT));
        int secondExpectedQuantity = 87;
        assertEquals(secondExpectedQuantity, operationHandler
                .countQuantity(SECOND_INITIAL_AMOUNT, SECOND_OPERATION_AMOUNT));
    }

    @Test
    void countQuantity_negativeOperationAmount_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(INITIAL_AMOUNT, NEGATIVE_OPERATION_AMOUNT));
        String expected = "Operation amount can not be less than 0";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void countQuantity_negativeCurrentAmount_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(NEGATIVE_OPERATION_AMOUNT, CURRENT_AMOUNT));
        String expected = "Current amount can not be less than 0";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void countQuantity_negativeResult_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.countQuantity(INITIAL_AMOUNT, CURRENT_AMOUNT));
        String expected = "Balance can not be less than 0";
        assertEquals(expected, exception.getMessage());
    }
}
