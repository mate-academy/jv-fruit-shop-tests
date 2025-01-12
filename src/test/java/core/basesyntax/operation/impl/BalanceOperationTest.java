package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler balanceOperation = new BalanceOperation();

    @Test
    void operationHandler_calculateCheck_ok() {
        assertEquals(10, balanceOperation.calculateOperation(10));
    }
}
