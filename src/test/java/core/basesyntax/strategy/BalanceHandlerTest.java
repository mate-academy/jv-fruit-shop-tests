package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceHandler();
        Map<String, OperationHandler> operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
    }

    @Test
    public void balanceHandler_ok() {
        int balance = 100;
        int quality = 10;
        int balanceResult = balance + quality;
        int actual = balanceHandler.handle(balance,quality);
        int expected = balanceResult;
        Assert.assertEquals(expected, actual);
    }
}
