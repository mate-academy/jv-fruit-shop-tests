package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationHandler operationHandler;
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> handlerMap;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        operationStrategy = new OperationStrategy(handlerMap);
    }

    @Test
    public void getOperation_operationIsBalance_isValid() {
        String operation = "b";
        operationHandler = new BalanceOperationHandler();
        handlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_operationIsPurchase_isValid() {
        String operation = "p";
        operationHandler = new PurchaseOperationHandler();
        handlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_operationIsSupply_isValid() {
        String operation = "s";
        operationHandler = new SupplyOperationHandler();
        handlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_operationIsReturn_isValid() {
        String operation = "r";
        operationHandler = new ReturnOperationHandler();
        handlerMap.put(operation, operationHandler);
        OperationHandler actual = operationHandler;
        OperationHandler expected = operationStrategy.getByOperation(operation);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        handlerMap.clear();
    }
}
