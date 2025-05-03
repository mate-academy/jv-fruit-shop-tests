package fruitshop.strategy.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.strategy.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler operationHandlerBalance;

    @BeforeEach
    void setUp() {
        operationHandlerBalance = new BalanceOperationHandler();
    }

    @Test
    void operate_validCase_ok() {
        int currentAmount = 0;
        int newAmount = 20;
        int expected = 20;
        int actual = operationHandlerBalance.operate(currentAmount, newAmount);
        assertEquals(expected, actual);
    }
}
