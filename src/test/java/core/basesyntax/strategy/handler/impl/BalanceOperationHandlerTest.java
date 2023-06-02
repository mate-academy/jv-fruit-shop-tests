package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler handler;
    private int oldValue;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
        oldValue = 500;
    }

    @Test
    void operate_positiveTransaction_ok() {
        int transaction = 100;
        int result = handler.operate(transaction, oldValue);
        assertEquals(transaction, result);
    }

    @Test
    void operate_negativeTransaction_notOk() {
        int transaction = -200;
        assertThrows(RuntimeException.class,
                () -> handler.operate(transaction, oldValue));
    }

    @Test
    void operate_zeroTransaction_ok() {
        int transaction = 0;
        int result = handler.operate(transaction, oldValue);
        assertEquals(transaction, result);
    }
}
