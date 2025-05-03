package core.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.exception.OperationHandlerException;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final SupplyOperationHandler handler = new SupplyOperationHandler();

    @Test
    public void testGetTransaction_PositiveAmount_ok() {
        int currentAmount = 100;
        int operationAmount = 50;

        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(150, result, "Balance should be 150 after supplying an amount of 50");
    }

    @Test
    public void testGetTransaction_NegativeAmount_ok() {
        int currentAmount = 100;
        int operationAmount = -50;

        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(50, result, "Balance should be 50 after supplying a negative amount of 50");
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
