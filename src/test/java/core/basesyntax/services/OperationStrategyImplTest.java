package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.operationhanlerservices.BalanceHandler;
import core.basesyntax.operationhanlerservices.OperationHandler;
import core.basesyntax.operationhanlerservices.PurchaseHandler;
import core.basesyntax.operationhanlerservices.ReturnHandler;
import core.basesyntax.operationhanlerservices.SupplayHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplayHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap) {
        };
    }

    @Test
    public void operationStrategy_getBalanceHandler_ok() {
        String expected = BalanceHandler.class.getName();
        String actual = operationStrategy.get(OperationType.BALANCE).getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_getSupplayHandler_ok() {
        String expected = SupplayHandler.class.getName();
        String actual = operationStrategy.get(OperationType.SUPPLY).getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_getReturnHandler_ok() {
        String expected = ReturnHandler.class.getName();
        String actual = operationStrategy.get(OperationType.RETURN).getClass().getName();
        assertEquals(expected, actual);

    }

    @Test
    public void operationStrategy_getPurchaseHandler_ok() {
        String expected = PurchaseHandler.class.getName();
        String actual = operationStrategy.get(OperationType.PURCHASE).getClass().getName();
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_getNull_isNotOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
        operationStrategy.get(null));
    }
}
