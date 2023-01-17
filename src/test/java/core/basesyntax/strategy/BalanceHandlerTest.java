package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static BalanceHandler balanceStrategy;
    private static final int BALANCE = 100;
    private static final int QUANTITY = 10;
    private static final int BALANCE_RESULT = BALANCE + QUANTITY;

    @BeforeClass
    public static void setUp() {
        balanceStrategy = new BalanceHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test
    public void balanceStrategy_ok() {
        int actual = balanceStrategy.handle(BALANCE, QUANTITY);
        int expected = BALANCE_RESULT;
        Assert.assertEquals(expected, actual);
    }
}
