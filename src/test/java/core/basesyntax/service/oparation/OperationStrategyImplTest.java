package core.basesyntax.service.oparation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.TypeOperations;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private String actual;
    private String expected;

    @BeforeClass
    public static void beforeClass() {
        Map<String, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(TypeOperations.BALANCE.get(), new BalanceHandler());
        operationHandlersMap.put(TypeOperations.SUPPLY.get(), new SupplyHandler());
        operationHandlersMap.put(TypeOperations.PURCHASE.get(), new PurchaseHandler());
        operationHandlersMap.put(TypeOperations.RETURN.get(), new ReturnHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @Test
    public void operationStrategyImplTest_Ok() {
        OperationHandler operationHandler = operationStrategy.get(TypeOperations.BALANCE);
        actual = operationHandler.getClass().getSimpleName();
        expected = "BalanceHandler";
        assertEquals(actual, expected);
    }

    @Test
    public void operationStrategyImplTest_notOk() {
        OperationHandler operationHandler = operationStrategy.get(TypeOperations.SUPPLY);
        actual = operationHandler.getClass().getSimpleName();
        expected = "BalanceHandler";
        assertNotEquals(actual, expected);
    }
}
