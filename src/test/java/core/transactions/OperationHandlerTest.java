package core.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.exception.OperationHandlerException;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    @Test
    public void testBalanceOperationHandler() {
        OperationHandler handler = new BalanceOperationHandler();
        int currentAmount = 100;
        int operationAmount = 50;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(150, result);
    }

    @Test
    public void testBalanceOperationHandler_NegativeResult() {
        OperationHandler handler = new BalanceOperationHandler();
        int currentAmount = 100;
        int operationAmount = -120;
        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        });
    }

    @Test
    public void testPurchaseOperationHandler() {
        OperationHandler handler = new PurchaseOperationHandler();
        int currentAmount = 200;
        int operationAmount = 50;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(150, result);
    }

    @Test
    public void testPurchaseOperationHandler_NegativeResult() {
        OperationHandler handler = new PurchaseOperationHandler();
        int currentAmount = 50;
        int operationAmount = 100;
        assertThrows(OperationHandlerException.class, () -> {
            handler.getTransaction(currentAmount, operationAmount);
        });
    }

    @Test
    public void testReturnOperationHandler() {
        OperationHandler handler = new ReturnOperationHandler();
        int currentAmount = 50;
        int operationAmount = 30;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(80, result);
    }

    @Test
    public void testSupplyOperationHandler() {
        OperationHandler handler = new SupplyOperationHandler();
        int currentAmount = 300;
        int operationAmount = 100;
        int result = handler.getTransaction(currentAmount, operationAmount);
        assertEquals(400, result);
    }
}
