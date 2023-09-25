package fruitshop.strategy.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.strategy.operation.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler operationHandlerPurchase;

    @BeforeEach
    void setUp() {
        operationHandlerPurchase = new PurchaseOperationHandler();
    }

    @Test
    void operate_validCase_ok() {
        int currentAmount = 100;
        int newAmount = 20;
        int expected = 80;
        int actual = operationHandlerPurchase.operate(currentAmount, newAmount);
        assertEquals(expected, actual);
    }

    @Test
    void operate_negativeResult_notOk() {
        int currentAmount = 20;
        int newAmount = 100;
        assertThrows(RuntimeException.class,
                () -> operationHandlerPurchase.operate(currentAmount, newAmount));
    }
}
