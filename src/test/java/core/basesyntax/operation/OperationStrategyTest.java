package core.basesyntax.operation;

import core.basesyntax.report.FruitBalance;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String INVALID_OPERATION = "h";
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new AdditionHandler());
        operationHandlerMap.put(SUPPLY, new AdditionHandler());
        operationHandlerMap.put(PURCHASE, new DecreaseHandler());
        operationHandlerMap.put(RETURN, new AdditionHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_HandlerByOperation_Ok() {
        Assert.assertEquals(operationStrategy.get(BALANCE).getClass(), AdditionHandler.class);
        Assert.assertEquals(operationStrategy.get(SUPPLY).getClass(), AdditionHandler.class);
        Assert.assertEquals(operationStrategy.get(RETURN).getClass(), AdditionHandler.class);
        Assert.assertEquals(operationStrategy.get(PURCHASE).getClass(), DecreaseHandler.class);
    }

    @Test (expected = RuntimeException.class)
    public void get_HandlerByInvalidOperation_NotOk() {
        operationStrategy.get(INVALID_OPERATION);
    }

    @After
    public void tearDown() {
        FruitBalance.FRUIT_BALANCE.clear();
    }
}
