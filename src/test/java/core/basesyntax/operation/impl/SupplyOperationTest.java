package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler supplyOperation = new SupplyOperation();

    @Test
    void operationHandler_Ok() {
        assertEquals(10, supplyOperation.calculateOperation(10));
    }
}
