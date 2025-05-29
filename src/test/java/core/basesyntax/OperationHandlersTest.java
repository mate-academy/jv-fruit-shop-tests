package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.handler.BalanceOperation;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperation;
import core.basesyntax.service.handler.ReturnOperation;
import core.basesyntax.service.handler.SupplyOperation;
import org.junit.jupiter.api.Test;

public class OperationHandlersTest {

    @Test
    void balanceHandler_returnsSameQuantity() {
        OperationHandler handler = new BalanceOperation();
        assertEquals(50, handler.handle(50));
    }

    @Test
    void purchaseHandler_returnsNegativeQuantity() {
        OperationHandler handler = new PurchaseOperation();
        assertEquals(-20, handler.handle(20));
    }

    @Test
    void returnHandler_returnsPositiveQuantity() {
        OperationHandler handler = new ReturnOperation();
        assertEquals(10, handler.handle(10));
    }

    @Test
    void supplyHandler_returnsPositiveQuantity() {
        OperationHandler handler = new SupplyOperation();
        assertEquals(30, handler.handle(30));
    }
}
