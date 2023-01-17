package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.ReturnHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandler returnHandler;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
        new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test
    public void returnHandler_ok() {
        int balance = 100;
        int quality = 10;
        int returnResult = balance + quality;
        int actual = returnHandler.handle(balance, quality);
        int expected = returnResult;
        Assert.assertEquals(expected, actual);
    }
}
