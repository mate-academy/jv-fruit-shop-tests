package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler handler;
    private int oldValue;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperationHandler();
        oldValue = 500;
    }

    @Test
    void operate_validTransaction_ok() {
        int transaction = 200;
        int result = handler.operate(transaction, oldValue);
        assertEquals(oldValue + transaction, result);
    }

    @Test
    void operate_zeroTransaction_ok() {
        int transaction = 0;
        int result = handler.operate(transaction, oldValue);
        assertEquals(oldValue + transaction, result);
    }

    @Test
    void operate_negativeTransaction_notOk() {
        int transaction = -100;
        assertThrows(RuntimeException.class,
                () -> handler.operate(transaction, oldValue));
    }
}
