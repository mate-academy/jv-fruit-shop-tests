package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
    }

    @Test
    void getOperationType_validQuantity_ok() {
        int quantity = 100;
        int expected = 100;
        int actual = operationHandler.getOperationType(quantity);
        assertEquals(expected, actual);
    }

    @Test
    void getOperationType_negativeQuantity_notOk() {
        int quantity = -100;
        Exception exception = assertThrows(RuntimeException.class,
                () -> operationHandler.getOperationType(quantity));
        assertEquals("Invalid data. Supply quantity can't be negative: "
                + quantity, exception.getMessage());
    }
}
