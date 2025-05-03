package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.OperationType;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private OperationHandler expected;
    private OperationHandler actual;

    @BeforeClass
    public static void beforeClass() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void operationStrategy_balanceOperation_ok() {
        expected = new BalanceOperationHandler();
        actual = operationStrategy.getOperationHandler(OperationType.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void operationStrategy_purchaseOperation_ok() {
        expected = new PurchaseOperationHandler();
        actual = operationStrategy.getOperationHandler(OperationType.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void operationStrategy_supplyOperation_ok() {
        expected = new SupplyOperationHandler();
        actual = operationStrategy.getOperationHandler(OperationType.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void operationStrategy_returnOperation_ok() {
        expected = new ReturnOperationHandler();
        actual = operationStrategy.getOperationHandler(OperationType.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
