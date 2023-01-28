package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.BalanceHandler;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @Test
    public void balanceHandlerTest_getOperationAction_OK() {
        balanceHandler = new BalanceHandler();
        int expected = 10;
        int actual = balanceHandler.getOperationAction(10);
        assertEquals(expected, actual);
    }
}
