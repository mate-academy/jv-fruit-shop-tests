package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.SupplierHandler;
import org.junit.Test;

public class SupplierHandlerTest {
    private static SupplierHandler supplierHandler;

    @Test
    public void supplierHandlerTest_getOperationAction_OK() {
        supplierHandler = new SupplierHandler();
        int expected = 10;
        int actual = supplierHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}
