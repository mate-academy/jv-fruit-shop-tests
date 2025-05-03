package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceStrategyWasReturned_ok() {
        OperationHandler operation = strategy.get(FruitTransaction.Operation.BALANCE);
        Class<? extends OperationHandler> actual = operation.getClass();
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseStrategyWasReturned_ok() {
        OperationHandler operation = strategy.get(FruitTransaction.Operation.PURCHASE);
        Class<? extends OperationHandler> actual = operation.getClass();
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnStrategyWasReturned_ok() {
        OperationHandler operation = strategy.get(FruitTransaction.Operation.RETURN);
        Class<? extends OperationHandler> actual = operation.getClass();
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyStrategyWasReturned_ok() {
        OperationHandler operation = strategy.get(FruitTransaction.Operation.SUPPLY);
        Class<? extends OperationHandler> actual = operation.getClass();
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        assertEquals(expected, actual);
    }
}
