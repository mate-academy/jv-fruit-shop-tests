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
    private static OperationHandler purchOp;
    private static OperationHandler balOp;
    private static OperationHandler returnOp;
    private static OperationHandler supplyOp;
    private static OperationHandler handler;

    @BeforeClass
    public static void setUp() {
        handlers = new HashMap<>();
        operationStrategy = new OperationStrategy(handlers);
    }

    @Test
    public void getByOperation_GetPurchaseHandlerBy_p_ok() {
        purchOp = new PurchaseOperationHandler();
        handlers.put("p", purchOp);
        handler = operationStrategy.getByOperation("p");
        assertEquals(handler, purchOp);
    }

    @Test
    public void getByOperation_GetBalanceHandlerBy_b_ok() {
        balOp = new BalanceOperationHandler();
        handlers.put("b", balOp);
        handler = operationStrategy.getByOperation("b");
        assertEquals(handler, balOp);
    }

    @Test
    public void getByOperation_GetReturnHandlerBy_r_ok() {
        returnOp = new ReturnOperationHandler();
        handlers.put("r", returnOp);
        handler = operationStrategy.getByOperation("r");
        assertEquals(handler, returnOp);
    }

    @Test
    public void getByOperation_GetSupplyHandlerBy_s_ok() {
        supplyOp = new SupplyOperationHandler();
        handlers.put("s", supplyOp);
        handler = operationStrategy.getByOperation("s");
        assertEquals(handler, supplyOp);
    }

    @After
    public void tearDown() {
        handlers.clear();
    }
}
