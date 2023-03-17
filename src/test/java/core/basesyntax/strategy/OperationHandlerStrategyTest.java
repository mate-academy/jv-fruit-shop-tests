package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyTest {
    private static OperationHandlerStrategy operationHandler;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandler = new OperationHandlerStrategy(handlers);
    }

    @Test(expected = RuntimeException.class)
    public void getHandler_nullOperation_notOk() {
        operationHandler.getHandler(null);
    }

    @Test
    public void getHandler_balanceOperation_ok() {
        OperationHandler expectedHandler = new BalanceOperationHandler();
        OperationHandler actualHandler =
                operationHandler.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getHandler_supplyOperation_ok() {
        OperationHandler expectedHandler = new SupplyOperationHandler();
        OperationHandler actualHandler =
                operationHandler.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getHandler_purchaseOperation_ok() {
        OperationHandler expectedHandler = new PurchaseOperationHandler();
        OperationHandler actualHandler =
                operationHandler.getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getHandler_returnOperation_ok() {
        OperationHandler expectedHandler = new ReturnOperationHandler();
        OperationHandler actualHandler =
                operationHandler.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
