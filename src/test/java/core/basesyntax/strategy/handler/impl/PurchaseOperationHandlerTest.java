package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler handler;
    private int oldValue;

    @BeforeEach
    void setUp() {
        handler = new PurchaseOperationHandler();
        oldValue = 500;
    }

    @Test
    void operate_validTransaction_ok() {
        int result = handler.operate(200, oldValue);
        int expected = 300;
        assertEquals(expected, result);
    }

    @Test
    void operate_transactionGreaterThanOldValue_notOk() {
        assertThrows(RuntimeException.class,
                () -> handler.operate(600, oldValue));
    }

    @Test
    void operate_transactionEqualsOldValue_ok() {
        int result = handler.operate(500, oldValue);
        int expected = 0;
        assertEquals(expected, result);
    }

    @Test
    void operate_negativeTransaction_notOk() {
        assertThrows(RuntimeException.class,
                () -> handler.operate(-100, oldValue));
    }
}
