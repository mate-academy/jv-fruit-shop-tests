package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class SupplierHandlerTest {
    private static SupplierHandler supplierHandler;

    @BeforeClass
    public static void beforeClass() {
        supplierHandler = new SupplierHandler();
    }

    @Test
    public void getOperationAction_IsGetSupplierData_OK() {
        int expected = 10;
        int actual = supplierHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}
