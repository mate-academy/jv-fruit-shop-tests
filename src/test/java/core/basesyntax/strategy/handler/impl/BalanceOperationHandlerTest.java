package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
    }

    @Test
    void operate_positiveTransaction_ok() {
        int result = handler.operate(100, 500);
        assertEquals(100, result);
    }

    @Test
    void operate_negativeTransaction_notOk() {
        assertThrows(RuntimeException.class,
                () -> handler.operate(-200, 500));
    }

    @Test
    void operate_zeroTransaction_ok() {
        int result = handler.operate(0, 500);
        assertEquals(0, result);
    }
}
