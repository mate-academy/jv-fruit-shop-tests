package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.PurchaseOperationHandler;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int TEST_QUANTITY = 20;
    private static final int TEST_CURRENT_QUANTITY = 50;
    private static final int TEST_EXPECTED_QUANTITY = 30;
    private final PurchaseOperationHandler purchaseOperation = new PurchaseOperationHandler();

    @Test
    public void apply_purchaseOperation_correctData_Ok() {
        int actual = purchaseOperation.apply(TEST_CURRENT_QUANTITY, TEST_QUANTITY);
        assertEquals(TEST_EXPECTED_QUANTITY, actual);
    }
}
