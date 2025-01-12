package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler purchaseOperation = new PurchaseOperation();

    @Test
    void operationHandler_calculateCheck_ok() {
        assertEquals(-10, purchaseOperation.calculateOperation(10));
    }
}
