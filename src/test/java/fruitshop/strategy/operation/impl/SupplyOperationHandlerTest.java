package fruitshop.strategy.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.strategy.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler operationHandlerSupply;

    @BeforeEach
    void setUp() {
        operationHandlerSupply = new SupplyOperationHandler();
    }

    @Test
    void operate_validCase_ok() {
        int currentAmount = 80;
        int newAmount = 20;
        int expected = 100;
        int actual = operationHandlerSupply.operate(currentAmount, newAmount);
        assertEquals(expected, actual);
    }
}
