package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void getOperationActionTest_GetBalance_OK() {
        int expected = 10;
        int actual = balanceHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}
