package core.basesyntax.strategy;

import static org.junit.Assert.assertSame;

import core.basesyntax.handlers.BalanceHandlerImpl;
import core.basesyntax.handlers.PurchaseHandlerImpl;
import core.basesyntax.handlers.ReturnHandlerImpl;
import core.basesyntax.handlers.SupplyHandlerImpl;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Before
    public void setUp() {
        operationHandler = null;
    }

    @Test
    public void getOperationHandler_getBalanceHandler_Ok() {
        operationHandler = operationStrategy.getOperationHandler(Operation.BALANCE);
        assertSame(operationHandler.getClass(), BalanceHandlerImpl.class);
    }

    @Test
    public void getOperationHandler_getPurchaseHandler_Ok() {
        operationHandler = operationStrategy.getOperationHandler(Operation.PURCHASE);
        assertSame(operationHandler.getClass(), PurchaseHandlerImpl.class);
    }

    @Test
    public void getOperationHandler_getReturnHandler_Ok() {
        operationHandler = operationStrategy.getOperationHandler(Operation.RETURN);
        assertSame(operationHandler.getClass(), ReturnHandlerImpl.class);
    }

    @Test
    public void getOperationHandler_getSupplyHandler_Ok() {
        operationHandler = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertSame(operationHandler.getClass(), SupplyHandlerImpl.class);
    }
}
