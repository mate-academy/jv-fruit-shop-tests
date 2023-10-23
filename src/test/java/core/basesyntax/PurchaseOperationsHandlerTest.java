package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.model.Operations;
import core.basesyntax.basesyntax.service.operations.PurchaseOperationsHandler;
import org.junit.jupiter.api.Test;

public class PurchaseOperationsHandlerTest {

    @Test
    void get_operationPurchaseFromPurchaseHandler_Ok() {
        PurchaseOperationsHandler handler = new PurchaseOperationsHandler();
        assertEquals(Operations.PURCHASE, handler.getOperation());
    }

    @Test
    void apply_operationFromPurchaseHandler_Ok() {
        PurchaseOperationsHandler handler = new PurchaseOperationsHandler();
        assertEquals(3, handler.applyOperation(7, 4));
    }
}

