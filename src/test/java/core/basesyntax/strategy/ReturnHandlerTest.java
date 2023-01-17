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
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static ReturnHandler returnStrategy;
    private static final int BALANCE = 100;
    private static final int QUANTITY = 10;
    private static final int RETURN_RESULT = BALANCE + QUANTITY;

    @BeforeClass
    public static void setUp() {
        returnStrategy = new ReturnHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test
    public void returnStrategy_ok() {
        int actual = returnStrategy.handle(BALANCE, QUANTITY);
        int expected = RETURN_RESULT;
        Assert.assertEquals(expected, actual);
    }
}
