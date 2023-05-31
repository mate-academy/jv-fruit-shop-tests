package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.OperationHandlerImplBalance;
import core.basesyntax.strategy.impl.OperationHandlerImplPurchase;
import core.basesyntax.strategy.impl.OperationHandlerImplReturn;
import core.basesyntax.strategy.impl.OperationHandlerImplStrategy;
import core.basesyntax.strategy.impl.OperationHandlerImplSupply;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerImplStrategyTest {
    private static OperationHandlerImplBalance operationHandlerImplBalance;
    private static OperationHandlerImplPurchase operationHandlerImplPurchase;
    private static OperationHandlerImplReturn operationHandlerImplReturn;
    private static OperationHandlerImplSupply operationHandlerImplSupply;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationHandlerImplStrategy operationHandlerImplStrategy;
    private static final int ZERO = 0;
    private static final int FIVE = 0;
    private static final int TEN = 0;

    @BeforeClass
    public static void setUp() {
        operationHandlerImplBalance = new OperationHandlerImplBalance();
        operationHandlerImplPurchase = new OperationHandlerImplPurchase();
        operationHandlerImplReturn = new OperationHandlerImplReturn();
        operationHandlerImplSupply = new OperationHandlerImplSupply();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new OperationHandlerImplBalance());
        operationHandlerImplStrategy = new OperationHandlerImplStrategy(operationHandlerMap);
    }

    @Test
    public void testBalance_Ok() {
        int actual = operationHandlerImplBalance.apply(FIVE,FIVE);
        int expected = TEN;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPurchase_Ok() {
        int actual = operationHandlerImplPurchase.apply(FIVE,FIVE);
        int expected = ZERO;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testReturn_Ok() {
        int actual = operationHandlerImplReturn.apply(FIVE,FIVE);
        int expected = TEN;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSupply_Ok() {
        int actual = operationHandlerImplSupply.apply(FIVE,FIVE);
        int expected = TEN;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStrategy_Ok() {
        OperationHandler actualHandler = operationHandlerImplStrategy.get("b");
        int actual = actualHandler.apply(FIVE,FIVE);
        int expected = TEN;
        Assert.assertEquals(expected, actual);
    }
}
