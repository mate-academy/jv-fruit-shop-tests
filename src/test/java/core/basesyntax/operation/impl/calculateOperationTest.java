package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CalculateOperationTest {
    private OperationHandler balanceOperation = new BalanceOperation();
    private OperationHandler purchaseOperation = new PurchaseOperation();
    private OperationHandler returnOperation = new ReturnOperation();
    private OperationHandler supplyOperation = new SupplyOperation();

    @Test
    void operationHandler_Ok() {
        assertEquals(10, balanceOperation.calculateOperation(10));
        assertEquals(-10, purchaseOperation.calculateOperation(10));
        assertEquals(10, supplyOperation.calculateOperation(10));
        assertEquals(10, returnOperation.calculateOperation(10));
    }
}
