package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.OperationHandlerStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static OperationHandlerStrategy operationHandlerStrategy;

    @BeforeClass
    public static void setUp() {
        operationHandlerStrategy = new OperationHandlerStrategyImpl();
    }

    @Test
    public void getBalanceHandler_ok() {
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("b")).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getPurchaseHandler_ok() {
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("p")).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getReturnHandler_ok() {
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("r")).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyHandler_ok() {
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = operationHandlerStrategy.get(FruitTransaction.Operation
                .getByName("s")).getClass();
        assertEquals(expected, actual);
    }
}
