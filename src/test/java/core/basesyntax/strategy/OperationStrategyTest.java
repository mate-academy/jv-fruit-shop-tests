package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static OperationHandler operationHandler;
    private static Map<String, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
    }

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void getOperation_operationIsBalance_isOk() {
        String operation = "b";
        operationHandler = new BalanceOperationHandler();
        operationHandlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_operationIsPurchase_isOk() {
        String operation = "p";
        operationHandler = new PurchaseOperationHandler();
        operationHandlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_operationIsSupply_isOk() {
        String operation = "s";
        operationHandler = new SupplyOperationHandler();
        operationHandlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_operationIsReturn_isOk() {
        String operation = "r";
        operationHandler = new ReturnOperationHandler();
        operationHandlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        operationHandlerMap.clear();
    }
}
