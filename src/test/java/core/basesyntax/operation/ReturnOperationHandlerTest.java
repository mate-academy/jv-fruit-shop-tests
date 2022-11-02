package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.ReturnOperationHandler;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final int TEST_QUANTITY = 20;
    private static final int TEST_CURRENT_QUANTITY = 50;
    private static final int TEST_EXPECTED_QUANTITY = 70;
    private final ReturnOperationHandler returnOperation = new ReturnOperationHandler();

    @Test
    public void apply_returnOperation_correctData_Ok() {
        int actual = returnOperation.apply(TEST_CURRENT_QUANTITY, TEST_QUANTITY);
        assertEquals(TEST_EXPECTED_QUANTITY, actual);
    }
}
