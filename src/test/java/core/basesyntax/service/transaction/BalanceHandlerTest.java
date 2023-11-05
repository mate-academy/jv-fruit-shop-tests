package core.basesyntax.service.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceHandler();
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
        try {
            operationHandler.countQuantity(10, -15);
        } catch (RuntimeException e) {
            String expected = "operation amount can not be less than 0";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void countQuantity_negativeCurrentAmount() {
        try {
            operationHandler.countQuantity(-15, 11);
        } catch (RuntimeException e) {
            String expected = "current amount can not be less than 0";
            assertEquals(expected, e.getMessage());
        }
    }
}
