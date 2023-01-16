package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static BalanceHandler balanceStrategy;
    private static PurchaseHandler purchaseStrategy;
    private static ReturnHandler returnStrategy;
    private static SupplyHandler supplyStrategy;
    private static final int BALANCE = 100;
    private static final int QUANTITY = 10;
    private static final int BALANCE_RESULT = BALANCE + QUANTITY;
    private static final int RETURN_RESULT = BALANCE + QUANTITY;
    private static final int SUPPLY_RESULT = BALANCE + QUANTITY;
    private static final int PURCHASE_RESULT = BALANCE - QUANTITY;

    @BeforeClass
    public static void setUp() {
        balanceStrategy = new BalanceHandler();
        purchaseStrategy = new PurchaseHandler();
        returnStrategy = new ReturnHandler();
        supplyStrategy = new SupplyHandler();
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

    @Test
    public void purchaseStrategy_ok() {
        int actual = purchaseStrategy.handle(BALANCE, QUANTITY);
        int expected = PURCHASE_RESULT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnStrategy_ok() {
        int actual = returnStrategy.handle(BALANCE, QUANTITY);
        int expected = RETURN_RESULT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyStrategy_ok() {
        int actual = supplyStrategy.handle(BALANCE, QUANTITY);
        int expected = SUPPLY_RESULT;
        Assert.assertEquals(expected, actual);
    }

}
