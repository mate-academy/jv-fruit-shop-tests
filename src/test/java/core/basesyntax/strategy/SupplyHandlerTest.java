package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandler supplyHandler;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
    }

    @Test
    public void supplyHandler_ok() {
        int balance = 100;
        int quality = 10;
        int supplyResult = balance + quality;
        int actual = supplyHandler.handle(balance,quality);
        int expected = supplyResult;
        Assert.assertEquals(expected, actual);
    }
}
