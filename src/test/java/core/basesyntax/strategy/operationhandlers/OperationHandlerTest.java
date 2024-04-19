package core.basesyntax.strategy.operationhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    @Test
    void getOperation_balanceReturnsQuantity_returnsPositiveResult() {
        BalanceOperationHandler handler = new BalanceOperationHandler();
        int expected = 10;
        int actual = handler.getOperation(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_supplyReturnsQuantity_returnsPositiveResult() {
        SupplyOperationHandler handler = new SupplyOperationHandler();
        int expected = 10;
        int actual = handler.getOperation(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_purchaseReturnsQuantity_returnsNegativeResult() {
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        int quantity = 10;
        int actual = handler.getOperation(quantity);
        int expected = -10;
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_returnReturnsQuantity_returnsPositiveResult() {
        ReturnOperationHandler handler = new ReturnOperationHandler();
        int expected = 10;
        int actual = handler.getOperation(expected);
        assertEquals(expected, actual);
    }
}
