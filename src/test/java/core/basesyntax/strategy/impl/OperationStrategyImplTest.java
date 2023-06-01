package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeAll
    public static void beforeAll() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        strategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void test_Get_BalanceHandler_ok() {
        BalanceHandler expected = new BalanceHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void test_Get_SupplyHandler_ok() {
        SupplyHandler expected = new SupplyHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void test_Get_PurchaseHandler_ok() {
        PurchaseHandler expected = new PurchaseHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void test_Get_ReturnHandler_ok() {
        ReturnHandler expected = new ReturnHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
