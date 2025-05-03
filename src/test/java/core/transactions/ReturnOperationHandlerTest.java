package core.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.exception.OperationHandlerException;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final ReturnOperationHandler handler = new ReturnOperationHandler();

    @Test
    public void testGetTransaction_PositiveAmount_ok() {
        int currentAmount = 50;
        int operationAmount = 30;

        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(80, result, "Balance should be 80 after returning an amount of 30");
    }

    @Test
    public void testGetTransaction_NegativeAmount_ok() {
        int currentAmount = 100;
        int operationAmount = -50;

        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(50, result, "Balance should be 50 after returning a negative amount of 50");
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
        int operationAmount = -150;

        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        }, "Should throw OperationHandlerException when resulting balance is negative");
    }
}
