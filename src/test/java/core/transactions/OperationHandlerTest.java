package core.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.exception.OperationHandlerException;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private static final OperationHandler handler = new BalanceOperationHandler();

    @Test
    public void testBalanceOperationHandler_ok() {
        int currentAmount = 100;
        int operationAmount = 50;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(150, result);
    }

    @Test
    public void testBalanceOperationHandler_NegativeResult_notOk() {
        int currentAmount = 100;
        int operationAmount = -120;
        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        });
    }

    @Test
    public void testPurchaseOperationHandler_ok() {
        int currentAmount = 200;
        int operationAmount = 50;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(250, result);
    }

    @Test
    public void testPurchaseOperationHandler_NegativeResult_notOk() {
        int currentAmount = 50;
        int operationAmount = -100;
        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        });
    }

    @Test
    public void testReturnOperationHandler_ok() {
        int currentAmount = 50;
        int operationAmount = 30;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(80, result);
    }

    @Test
    public void testSupplyOperationHandler_ok() {
        int currentAmount = 300;
        int operationAmount = 100;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(400, result);
    }
}
