package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IncreaseOperationHandlerTest {
    private static IncreaseOperationHandler increaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        increaseOperationHandler = new IncreaseOperationHandler();
    }

    @Test
    void operate_AdditionRegularCase_Ok() {
        assertEquals(10, increaseOperationHandler.operate(5, 5));
    }

    @Test
    void operate_AdditionZero_Ok() {
        assertEquals(10, increaseOperationHandler.operate(10, 0));
    }

    @Test
    void operate_AdditionNegative_NotOk() {
        assertThrows(RuntimeException.class, () -> increaseOperationHandler.operate(10, -10));
    }
}
