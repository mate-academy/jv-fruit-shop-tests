package core.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.exception.OperationHandlerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private BalanceOperationHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new BalanceOperationHandler();
    }
    @Test
    public void testGetTransaction_PositiveAmount_ok() {
        int currentAmount = 100;
        int operationAmount = 50;

        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(150, result, "Balance should be 150 after depositing 50");
    }

    @Test
    public void testGetTransaction_NegativeAmount_notOk() {
        int currentAmount = 100;
        int operationAmount = -150;

        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        }, "Should throw OperationHandlerException when trying to withdraw more than the balance");
    }

    @Test
    public void testGetTransaction_ZeroAmount_ok() {
        int currentAmount = 100;
        int operationAmount = 0;

        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(100, result,
                "Balance should remain unchanged when performing a zero transaction");
    }

    @Test
    public void testGetTransaction_NegativeResult_notOk() {
        int currentAmount = 100;
        int operationAmount = -200;

        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        }, "Should throw OperationHandlerException when resulting balance is negative");
    }
}
