package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.OperationHandlerStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static OperationHandlerStrategy operationHandlerStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> map;

    @BeforeClass
    public static void setUp() {
        map = new HashMap<>();

        {
            map.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
            map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
            map.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
            map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        }

        operationHandlerStrategy = new OperationHandlerStrategyImpl();
    }

    @Test
    public void getBalanceHandler_ok() {
        Class<?> expected = map.get(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("b")).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getPurchaseHandler_ok() {
        Class<?> expected = map.get(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("p")).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getReturnHandler_ok() {
        Class<?> expected = map.get(FruitTransaction.Operation.RETURN).getClass();
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("r")).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyHandler_ok() {
        Class<?> expected = map.get(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("s")).getClass();
        assertEquals(expected, actual);
    }
}
