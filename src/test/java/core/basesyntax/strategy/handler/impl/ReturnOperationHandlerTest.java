package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler handler;
    private int oldValue;

    @BeforeEach
    void setUp() {
        handler = new ReturnOperationHandler();
        oldValue = 500;
    }

    @Test
    void operate_validTransaction_ok() {
        int result = handler.operate(200, oldValue);
        int expected = 700;
        assertEquals(expected, result);
    }

    @Test
    void operate_zeroTransaction_ok() {
        int result = handler.operate(0, oldValue);
        int expected = 500;
        assertEquals(expected, result);
    }

    @Test
    void operate_negativeTransaction_notOk() {
        assertThrows(RuntimeException.class,
                () -> handler.operate(-100, oldValue));
    }
}
