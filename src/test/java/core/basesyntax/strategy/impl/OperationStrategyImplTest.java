package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeAll
    public static void beforeAll() {
        handlerMap = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler());
        strategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void getHandler_BalanceHandler_ok() {
        BalanceHandler expected = new BalanceHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_SupplyHandler_ok() {
        SupplyHandler expected = new SupplyHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_PurchaseHandler_ok() {
        PurchaseHandler expected = new PurchaseHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void getHandler_ReturnHandler_ok() {
        ReturnHandler expected = new ReturnHandler();
        OperationHandler actual
                = strategy.getHandler(FruitTransaction.Operation.RETURN);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
