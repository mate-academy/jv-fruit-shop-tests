package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private OperationHandler returnOperation = new ReturnOperation();

    @Test
    void operationHandler_Ok() {
        assertEquals(10, returnOperation.calculateOperation(10));
    }
}
