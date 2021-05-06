package core.basesyntax;

import core.basesyntax.handler.DecreaseOperationHandler;
import core.basesyntax.handler.IncreaseOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<OperationType, OperationHandler> operationStrategyMap = new HashMap<>();
    private static OperationStrategy operationStrategy;
    private static OperationHandler increaseHandler;
    private static OperationHandler decreaseHandler;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        increaseHandler = new IncreaseOperationHandler();
        decreaseHandler = new DecreaseOperationHandler();

        operationStrategyMap.put(OperationType.BALANCE, increaseHandler);
        operationStrategyMap.put(OperationType.RETURN, increaseHandler);
        operationStrategyMap.put(OperationType.SUPPLY, increaseHandler);
        operationStrategyMap.put(OperationType.PURCHASE, decreaseHandler);
    }

    @Test
    public void getHandler_IncreaseOperationHandler_isOk() {
        OperationHandler expectedHandler = operationStrategy.getHandler(OperationType.SUPPLY);
        OperationHandler actualHandler = operationStrategyMap.get(OperationType.SUPPLY);
        Assert.assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void getHandler_DecreaseOperationHandler_isOk() {
        OperationHandler expectedHandler = operationStrategy.getHandler(OperationType.PURCHASE);
        OperationHandler actualHandler = operationStrategyMap.get(OperationType.PURCHASE);
        Assert.assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void getHandler_WrongOperationHandler_NotOk() {
        OperationHandler expectedHandler = operationStrategy.getHandler(OperationType.RETURN);
        OperationHandler actualHandler = operationStrategyMap.get(OperationType.PURCHASE);
        Assert.assertNotEquals(expectedHandler, actualHandler);
        Assert.assertNotEquals(expectedHandler, null);
    }
}
