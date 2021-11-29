package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.OperationType;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE.getShortOperation(),
                new BalanceHandler());
        operationHandlerMap.put(OperationType.PURCHASE.getShortOperation(),
                new PurchaseHandler());
        operationHandlerMap.put(OperationType.RETURN.getShortOperation(),
                new ObtainingHandler());
        operationHandlerMap.put(OperationType.SUPPLY.getShortOperation(),
                new ObtainingHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperation_Balance_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                OperationType.BALANCE.getShortOperation());
        OperationHandler expected = new BalanceHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperation_Supply_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                OperationType.SUPPLY.getShortOperation());
        OperationHandler expected = new ObtainingHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperation_Purchase_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                OperationType.PURCHASE.getShortOperation());
        OperationHandler expected = new PurchaseHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getOperation_Return_Ok() {
        OperationHandler actual = operationStrategy.getOperation(
                OperationType.RETURN.getShortOperation());
        OperationHandler expected = new ObtainingHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_InvalidOperation_NotOk() {
        operationStrategy.getOperation("k");
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_EmptyLine_NotOk() {
        operationStrategy.getOperation("");
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_Null_Ok() {
        operationStrategy.getOperation(null);
    }
}
