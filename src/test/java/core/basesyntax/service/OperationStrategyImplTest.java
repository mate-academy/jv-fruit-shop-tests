package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandler;
import core.basesyntax.service.operation.ReturnHandler;
import core.basesyntax.service.operation.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> operationHandlerMap;
    private OperationHandler expected;
    private OperationHandler actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Before
    public void setUp() throws Exception {
        operationHandlerMap.put("b", new BalanceHandler());
        operationHandlerMap.put("s", new SupplyHandler());
        operationHandlerMap.put("p", new PurchaseHandler());
        operationHandlerMap.put("r", new ReturnHandler());
    }

    @Test
    public void getHandler_ok() {
        expected = operationHandlerMap.get("b");
        actual = operationStrategy.getHandler("b");
        assertEquals(expected, actual);
        expected = operationHandlerMap.get("s");
        actual = operationStrategy.getHandler("s");
        assertEquals(expected, actual);
        expected = operationHandlerMap.get("p");
        actual = operationStrategy.getHandler("p");
        assertEquals(expected, actual);
        expected = operationHandlerMap.get("r");
        actual = operationStrategy.getHandler("r");
        assertEquals(expected, actual);
    }
}
