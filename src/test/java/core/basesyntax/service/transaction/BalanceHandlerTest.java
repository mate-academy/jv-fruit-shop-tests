package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final int INITIAL_AMOUNT = 0;
    private static final int OPERATION_AMOUNT = 11;
    private static final int FIRST_OPERATION_AMOUNT = 15;
    private static final int SECOND_OPERATION_AMOUNT = 13;
    private static final int NEGATIVE_OPERATION_AMOUNT = -13;
    private static final int CURRENT_AMOUNT = 100;
    private static final int NEGATIVE_CURRENT_AMOUNT = -15;
    private static final int FIRST_EXPECTED_QUANTITY = 15;
    private static final int SECOND_EXPECTED_QUANTITY = 113;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = FIRST_EXPECTED_QUANTITY;
        assertEquals(firstExpectedQuantity, operationHandler
                .countQuantity(INITIAL_AMOUNT, FIRST_OPERATION_AMOUNT));
        int secondExpectedQuantity = SECOND_EXPECTED_QUANTITY;
        assertEquals(secondExpectedQuantity, operationHandler
                .countQuantity(CURRENT_AMOUNT, SECOND_OPERATION_AMOUNT));
    }

    @Test
    void countQuantity_negativeOperationAmount_notOk() {
        try {
            operationHandler.countQuantity(INITIAL_AMOUNT, NEGATIVE_OPERATION_AMOUNT);
        } catch (RuntimeException e) {
            String expected = "Operation amount can not be less than 0";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void countQuantity_negativeCurrentAmount() {
        try {
            operationHandler.countQuantity(NEGATIVE_CURRENT_AMOUNT, OPERATION_AMOUNT);
        } catch (RuntimeException e) {
            String expected = "Current amount can not be less than 0";
            assertEquals(expected, e.getMessage());
        }
    }
}
