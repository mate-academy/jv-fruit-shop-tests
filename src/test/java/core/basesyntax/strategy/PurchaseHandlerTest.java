package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
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
