package service;

import static org.junit.Assert.assertEquals;

import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;
import service.strategy.BalanceOperationHandler;
import service.strategy.PurchaseOperationHandler;
import service.strategy.ReturnOperationHandler;
import service.strategy.SupplyOperationHandler;

public class OperationHandlerStrategyImplTest {
    private static OperationHandlerStrategy operationHandlerStrategy;

    @Before
    public void setUp() {
        operationHandlerStrategy = new OperationHandlerStrategyImpl();
    }

    @Test
    public void get_balanceHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expected = BalanceOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expected = PurchaseOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_supplyHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expected = SupplyOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnHandler_Ok() {
        Class<?> actual = operationHandlerStrategy
                .get(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expected = ReturnOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_null_NotOk() {
        operationHandlerStrategy.get(null);
    }
}
