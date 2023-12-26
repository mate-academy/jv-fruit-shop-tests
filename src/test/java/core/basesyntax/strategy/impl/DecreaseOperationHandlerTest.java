package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DecreaseOperationHandlerTest {
    private static OperationHandler decreaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        decreaseOperationHandler = new DecreaseOperationHandler();
    }

    @Test
    void operate_RegularCase_Ok() {
        int available = 10;
        int toBuy = 5;
        int actual = decreaseOperationHandler.operate(available, toBuy);
        assertEquals(available - toBuy, actual);
    }

    @Test
    void operate_SubtractEqualValue_Ok() {
        assertDoesNotThrow(() -> decreaseOperationHandler.operate(10, 10));
    }

    @Test
    void operate_SubtractZero_Ok() {
        assertEquals(10, decreaseOperationHandler.operate(10, 0));
    }

    @Test
    void operate_NegativeDifference_NotOk() {
        assertThrows(RuntimeException.class, () -> decreaseOperationHandler.operate(10, 15),
                "Quantity can't be negative");
    }

    @Test
    void operate_SubtractNegativeValue_NotOk() {
        assertThrows(RuntimeException.class, () -> decreaseOperationHandler.checkQuantity(10, -5),
                "Subtrahend is negative");
    }
}
