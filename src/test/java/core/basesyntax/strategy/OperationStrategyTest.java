package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> handlers;
    private static OperationHandler purchaseOperationHandler;
    private static OperationHandler balanceOperationHandler;
    private static OperationHandler returnOperationHandler;
    private static OperationHandler supplyOperationHandler;
    private static OperationHandler handler;

    @BeforeClass
    public static void setUp() {
        handlers = new HashMap<>();
        operationStrategy = new OperationStrategy(handlers);
    }

    @Test
    public void getByOperation_GetPurchaseHandlerBy_p_ok() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        handlers.put("p", purchaseOperationHandler);
        handler = operationStrategy.getByOperation("p");
        assertEquals(handler, purchaseOperationHandler);
    }

    @Test
    public void getByOperation_GetBalanceHandlerBy_b_ok() {
        balanceOperationHandler = new BalanceOperationHandler();
        handlers.put("b", balanceOperationHandler);
        handler = operationStrategy.getByOperation("b");
        assertEquals(handler, balanceOperationHandler);
    }

    @Test
    public void getByOperation_GetReturnHandlerBy_r_ok() {
        returnOperationHandler = new ReturnOperationHandler();
        handlers.put("r", returnOperationHandler);
        handler = operationStrategy.getByOperation("r");
        assertEquals(handler, returnOperationHandler);
    }

    @Test
    public void getByOperation_GetSupplyHandlerBy_s_ok() {
        supplyOperationHandler = new SupplyOperationHandler();
        handlers.put("s", supplyOperationHandler);
        handler = operationStrategy.getByOperation("s");
        assertEquals(handler, supplyOperationHandler);
    }

    @After
    public void tearDown() {
        handlers.clear();
    }
}
