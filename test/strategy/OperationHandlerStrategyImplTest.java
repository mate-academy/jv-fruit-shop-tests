import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.OperationHandlerPurchaseImpl;
import core.basesyntax.strategy.impl.OperationHandlerReturnImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.OperationHandlerSupplyImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OperationHandlerStrategyImplTest {
    private static OperationHandlerBalanceImpl testBalanceStrategy;
    private static OperationHandlerReturnImpl testReturnStrategy;
    private static OperationHandlerSupplyImpl testSupplyStrategy;
    private static OperationHandlerPurchaseImpl testPurchaseStrategy;
    private static Map<String, OperationHandler> testOperationHandler;
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static final int BALANCE = 100;
    private static final int COUNT = 10;
    private static final int BALANCE_RESULT = 110;
    private static final int RETURN_RESULT = 110;
    private static final int SUPPLY_RESULT = 110;
    private static final int PURCHASE_RESULT = 90;


    @BeforeClass
    public static void setUp() {
        testBalanceStrategy = new OperationHandlerBalanceImpl();
        testPurchaseStrategy = new OperationHandlerPurchaseImpl();
        testSupplyStrategy = new OperationHandlerSupplyImpl();
        testReturnStrategy = new OperationHandlerReturnImpl();
        testOperationHandler = new HashMap<>();
        testOperationHandler.put("b", new OperationHandlerBalanceImpl());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(testOperationHandler);
    }

    @Test
    public void testBalanceStrategy_ok() {
        int actual = testBalanceStrategy.apply(BALANCE, COUNT);
        int expected = BALANCE_RESULT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPurchaseStrategy_ok() {
        int actual = testPurchaseStrategy.apply(BALANCE, COUNT);
        int expected = PURCHASE_RESULT;
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void tesReturnStrategy_ok() {
        int actual = testReturnStrategy.apply(BALANCE, COUNT);
        int expected = RETURN_RESULT;
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testSupplyStrategy_ok() {
        int actual = testSupplyStrategy.apply(BALANCE, COUNT);
        int expected = SUPPLY_RESULT;
        Assert.assertEquals(expected, actual);
    }
}
