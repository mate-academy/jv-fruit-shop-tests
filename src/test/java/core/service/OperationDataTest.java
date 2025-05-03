package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OperationDataTest {
    private static final OperationData operationData =
            new OperationData(OperationType.B, "berry", 1);

    @Test
    void getOperation_ok() {
        assertEquals(OperationType.B, operationData.getOperationType());
    }

    @Test
    void getFruit_ok() {
        assertEquals("berry", operationData.getProduct());
    }

    @Test
    void getQuantity_ok() {
        assertEquals(1, operationData.getQuantity());
    }
}
