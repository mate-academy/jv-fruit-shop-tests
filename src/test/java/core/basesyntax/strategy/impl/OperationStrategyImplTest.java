package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertSame;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceHandler_ok() {
        OperationHandler actualOperationHandler = operationStrategy.get(Operation.BALANCE);
        assertSame(BalanceHandler.class, actualOperationHandler.getClass());
    }

    @Test
    public void get_supplyHandler_ok() {
        OperationHandler actualOperationHandler = operationStrategy.get(Operation.SUPPLY);
        assertSame(SupplyHandler.class, actualOperationHandler.getClass());
    }

    @Test
    public void get_purchaseHandler_ok() {
        OperationHandler actualOperationHandler = operationStrategy.get(Operation.PURCHASE);
        assertSame(PurchaseHandler.class, actualOperationHandler.getClass());
    }

    @Test
    public void get_returnHandler_ok() {
        OperationHandler actualOperationHandler = operationStrategy.get(Operation.RETURN);
        assertSame(ReturnHandler.class, actualOperationHandler.getClass());
    }
}
