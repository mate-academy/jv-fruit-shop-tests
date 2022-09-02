package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.OperationHandlerImplBalance;
import core.basesyntax.strategy.impl.OperationHandlerImplPurchase;
import core.basesyntax.strategy.impl.OperationHandlerImplReturn;
import core.basesyntax.strategy.impl.OperationHandlerImplStrategy;
import core.basesyntax.strategy.impl.OperationHandlerImplSupply;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerImplStrategyTest {
    private OperationHandlerImplBalance operationHandlerImplBalance;
    private OperationHandlerImplPurchase operationHandlerImplPurchase;
    private OperationHandlerImplReturn operationHandlerImplReturn;
    private OperationHandlerImplSupply operationHandlerImplSupply;
    private Map<String, OperationHandler> operationHandlerMap;
    private OperationHandlerImplStrategy operationHandlerImplStrategy;

    @Before
    public void setUp() {
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
        int actual = operationHandlerImplBalance.apply(5,5);
        Assert.assertEquals(10, actual);
    }

    @Test
    public void testPurchase_Ok() {
        int actual = operationHandlerImplPurchase.apply(5,5);
        Assert.assertEquals(0, actual);
    }

    @Test
    public void testReturn_Ok() {
        int actual = operationHandlerImplReturn.apply(5,5);
        Assert.assertEquals(10, actual);
    }

    @Test
    public void testSupply_Ok() {
        int actual = operationHandlerImplSupply.apply(5,5);
        Assert.assertEquals(10, actual);
    }

    @Test
    public void testStrategy_Ok() {
        OperationHandler actualHandler = operationHandlerImplStrategy.get("b");
        int actual = actualHandler.apply(5,5);
        Assert.assertEquals(10, actual);
    }
}
