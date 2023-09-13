package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SetOperationHandlerTest {
    private static OperationHandler setOperationHandler;

    @BeforeAll
    static void beforeAll() {
        setOperationHandler = new SetOperationHandler();
    }

    @Test
    void operate_SetQuantityRegularCase_Ok() {
        assertEquals(10, setOperationHandler.operate(0, 10));
    }

    @Test
    void operate_SetZeroQuantity_Ok() {
        assertDoesNotThrow(() -> setOperationHandler.operate(0, 0));
    }

    @Test
    void operate_SetNegativeQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> setOperationHandler.operate(0, -10),
                "can't set negative quantity");
    }
}
