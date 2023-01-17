package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static SupplyHandler supplyStrategy;
    private static final int BALANCE = 100;
    private static final int QUANTITY = 10;
    private static final int SUPPLY_RESULT = BALANCE + QUANTITY;

    @BeforeClass
    public static void setUp() {
        supplyStrategy = new SupplyHandler();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandler());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test
    public void supplyStrategy_ok() {
        int actual = supplyStrategy.handle(BALANCE, QUANTITY);
        int expected = SUPPLY_RESULT;
        Assert.assertEquals(expected, actual);
    }

}
