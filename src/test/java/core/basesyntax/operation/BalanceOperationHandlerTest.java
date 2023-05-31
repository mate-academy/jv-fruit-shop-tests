package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.BalanceOperationHandler;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final int TEST_QUANTITY = 20;
    private static final int TEST_CURRENT_QUANTITY = 10;
    private static final int TEST_EXPECTED_QUANTITY = 30;
    private final BalanceOperationHandler balanceOperation = new BalanceOperationHandler();

    @Test
    public void apply_balanceOperation_correctData_Ok() {
        int actual = balanceOperation.apply(TEST_CURRENT_QUANTITY, TEST_QUANTITY);
        assertEquals(TEST_EXPECTED_QUANTITY, actual);
    }
}
