package core.basesyntax.strategy;

import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static Strategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler());
        operationHandlerMap.put("p", new PurchaseOperationHandler());
        operationHandlerMap.put("r", new ReturnOperationHandler());
        operationHandlerMap.put("s", new SupplyOperationHandler());
        operationStrategy = new StrategyImpl(operationHandlerMap);
    }

    @Test
    public void balanceClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("b").getClass();
        Assert.assertEquals(BalanceOperationHandler.class, actual);
    }

    @Test
    public void purchaseClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("p").getClass();
        Assert.assertEquals(PurchaseOperationHandler.class, actual);
    }

    @Test
    public void returnClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("r").getClass();
        Assert.assertEquals(ReturnOperationHandler.class, actual);
    }

    @Test
    public void supplyClass_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy.get("s").getClass();
        Assert.assertEquals(SupplyOperationHandler.class, actual);
    }

    @Test
    public void invalidInputValue_notOk() {
        Assert.assertNull(operationStrategy.get("invalid"));
    }
}
