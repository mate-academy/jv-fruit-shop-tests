package core.basesyntax.strategy;

import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static Map<String, OperationHandler> operationHandler;
    private static OperationHandlerStrategyImpl operationHandlerStrategy;
    private static BalanceHandlerImpl balanceStrategy;
    private static PurchaseHandlerImpl purchaseStrategy;
    private static ReturnHandlerImpl returnStrategy;
    private static SupplyHandlerImpl supplyStrategy;
    private static final int BALANCE = 100;
    private static final int QUANTITY = 10;
    private static final int BALANCE_RESULT = BALANCE + QUANTITY;
    private static final int RETURN_RESULT = BALANCE + QUANTITY;
    private static final int SUPPLY_RESULT = BALANCE + QUANTITY;
    private static final int PURCHASE_RESULT = BALANCE - QUANTITY;

    @BeforeClass
    public static void setUp() {
        balanceStrategy = new BalanceHandlerImpl();
        purchaseStrategy = new PurchaseHandlerImpl();
        returnStrategy = new ReturnHandlerImpl();
        supplyStrategy = new SupplyHandlerImpl();
        operationHandler = new HashMap<>();
        operationHandler.put("b", new BalanceHandlerImpl());
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandler);
    }

    @Test
    public void balanceStrategy_ok() {
        int actual = balanceStrategy.apply(BALANCE, QUANTITY);
        int expected = BALANCE_RESULT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseStrategy_ok() {
        int actual = purchaseStrategy.apply(BALANCE, QUANTITY);
        int expected = PURCHASE_RESULT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnStrategy_ok() {
        int actual = returnStrategy.apply(BALANCE, QUANTITY);
        int expected = RETURN_RESULT;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyStrategy_ok() {
        int actual = supplyStrategy.apply(BALANCE, QUANTITY);
        int expected = SUPPLY_RESULT;
        Assert.assertEquals(expected, actual);
    }

}
