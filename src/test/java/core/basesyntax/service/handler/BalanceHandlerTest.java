package core.basesyntax.service.handler;

import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceHandler = new BalanceHandler();
    }

    @Test(expected = RuntimeException.class)
    public void applyNullValue_NotOk() {
        balanceHandler.apply(null);
    }
}
