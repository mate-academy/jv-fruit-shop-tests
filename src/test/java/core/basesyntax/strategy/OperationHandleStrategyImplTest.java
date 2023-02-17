package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.BalanceOperationHandleImpl;
import core.basesyntax.strategy.impl.OperationHandleStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandleImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandleImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandleImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandleStrategyImplTest {
    private static BalanceOperationHandleImpl testBalanceStrategy;
    private static ReturnOperationHandleImpl testReturnStrategy;
    private static SupplyOperationHandleImpl testSupplyStrategy;
    private static PurchaseOperationHandleImpl testPurchaseStrategy;
    private static Map<Operation, OperationHandler> testOperationHandler;
    private static OperationHandleStrategyImpl operationHandlerStrategy;
    private static final int BALANCE = 100;
    private static final int COUNT = 10;
    private static final int BALANCE_RESULT = 110;
    private static final int RETURN_RESULT = 110;
    private static final int SUPPLY_RESULT = 110;
    private static final int PURCHASE_RESULT = 90;

    @BeforeClass
    public static void setUp() {
        testBalanceStrategy = new BalanceOperationHandleImpl();
        testPurchaseStrategy = new PurchaseOperationHandleImpl();
        testSupplyStrategy = new SupplyOperationHandleImpl();
        testReturnStrategy = new ReturnOperationHandleImpl();
        testOperationHandler = new HashMap<>();
        testOperationHandler.put(Operation.BALANCE, new BalanceOperationHandleImpl());
        operationHandlerStrategy = new OperationHandleStrategyImpl(testOperationHandler);
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
