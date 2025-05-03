package fruitshop.strategy.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.strategy.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler operationHandlerReturn;

    @BeforeEach
    void setUp() {
        operationHandlerReturn = new ReturnOperationHandler();
    }

    @Test
    void operate_validCase_ok() {
        int currentAmount = 100;
        int newAmount = 20;
        int expected = 120;
        int actual = operationHandlerReturn.operate(currentAmount, newAmount);
        assertEquals(expected, actual);
    }
}
