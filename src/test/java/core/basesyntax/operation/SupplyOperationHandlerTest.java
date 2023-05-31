package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.SupplyOperationHandler;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final int TEST_QUANTITY = 20;
    private static final int TEST_CURRENT_QUANTITY = 40;
    private static final int TEST_EXPECTED_QUANTITY = 60;
    private final SupplyOperationHandler supplyOperation = new SupplyOperationHandler();

    @Test
    public void apply_supplyOperation_correctData_Ok() {
        int actual = supplyOperation.apply(TEST_CURRENT_QUANTITY, TEST_QUANTITY);
        assertEquals(TEST_EXPECTED_QUANTITY, actual);
    }
}
