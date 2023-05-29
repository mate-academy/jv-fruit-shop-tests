package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.PurchaseHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    public void purchaseHandler_ok() {
        int balance = 100;
        int quality = 10;
        int purchaseResult = balance - quality;
        int actual = purchaseHandler.handle(balance, quality);
        int expected = purchaseResult;
        Assert.assertEquals(expected, actual);
    }
}
