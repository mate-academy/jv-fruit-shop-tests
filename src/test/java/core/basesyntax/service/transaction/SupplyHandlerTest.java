package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {

    private static final int ZERO_AMOUNT = 0;
    private static final int FIFTEEN_AMOUNT = 15;
    private static final int HUNDRED_AMOUNT = 100;
    private static final int THIRTEEN_AMOUNT = 13;
    private static final int ONE_HUNDRED_THIRTEEN_AMOUNT = 113;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyHandler();
    }

    @Test
    void countQuantity_validQuantity_ok() {
        int firstExpectedQuantity = FIFTEEN_AMOUNT;
        assertEquals(firstExpectedQuantity,
                operationHandler.countQuantity(ZERO_AMOUNT, FIFTEEN_AMOUNT));
        int secondExpectedQuantity = ONE_HUNDRED_THIRTEEN_AMOUNT;
        assertEquals(secondExpectedQuantity,
                operationHandler.countQuantity(HUNDRED_AMOUNT, THIRTEEN_AMOUNT));
    }

    @Test
    void countQuantity_negativeOperationAmount_notOk() {
        String expectedErrorMessage = "Operation amount can not be less than 0";
        assertThrows(RuntimeException.class, () ->
                operationHandler
                        .countQuantity(HUNDRED_AMOUNT, -FIFTEEN_AMOUNT), expectedErrorMessage);
    }

    @Test
    void countQuantity_negativeCurrentAmount() {
        String expectedErrorMessage = "Current amount can not be less than 0";
        assertThrows(RuntimeException.class, () ->
                operationHandler
                        .countQuantity(-FIFTEEN_AMOUNT, HUNDRED_AMOUNT), expectedErrorMessage);
    }
}
