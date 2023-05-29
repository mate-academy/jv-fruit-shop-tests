package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceHandler();
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
