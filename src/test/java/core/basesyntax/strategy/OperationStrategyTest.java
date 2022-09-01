package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler());
        operationHandlerMap.put("p", new PurchaseOperationHandler());
        operationHandlerMap.put("r", new ReturnOperationHandler());
        operationHandlerMap.put("s", new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void returnClassOfBalanceOperationHandler_ok() {
        Class balanceClass = operationStrategy.getByOperation("b").getClass();
        assertEquals("Expected another class BalanceOperationHandler",
                balanceClass,
                BalanceOperationHandler.class);
    }

    @Test
    public void returnClassOfPurchaseOperationHandler_ok() {
        Class purchaseClass = operationStrategy.getByOperation("p").getClass();
        assertEquals("Expected another class PurchaseOperationHandler",
                purchaseClass,
                PurchaseOperationHandler.class);
    }

    @Test
    public void returnClassOfReturnOperationHandler_ok() {
        Class returnClass = operationStrategy.getByOperation("r").getClass();
        assertEquals("Expected another class ReturnOperationHandler",
                returnClass,
                ReturnOperationHandler.class);
    }

    @Test
    public void returnClassOfSupplyOperationHandler_ok() {
        Class supplyClass = operationStrategy.getByOperation("s").getClass();
        assertEquals("Expected another class SupplyOperationHandler",
                supplyClass,
                SupplyOperationHandler.class);
    }
}
