package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationHandler_balanceOperation_ok() {
        Class<?> expected = BalanceOperationHandler.class;
        Class<?> actual = operationStrategy.getOperationHandler(FruitTransaction
                .Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationHandler_purchaseOperation_ok() {
        Class<?> expected = PurchaseOperationHandler.class;
        Class<?> actual = operationStrategy.getOperationHandler(FruitTransaction
                .Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationHandler_supplyOperation_ok() {
        Class<?> expected = SupplyOperationHandler.class;
        Class<?> actual = operationStrategy.getOperationHandler(FruitTransaction
                .Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationHandler_returnOperation_ok() {
        Class<?> expected = ReturnOperationHandler.class;
        Class<?> actual = operationStrategy.getOperationHandler(FruitTransaction
                .Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void getOperationHandler_nullOperation_notOk() {
        operationStrategy.getOperationHandler(null);
    }
}
