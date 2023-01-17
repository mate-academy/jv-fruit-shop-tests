package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void init() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getOperationHandler_balanceOperation_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<?> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(String.format("Should return %s class but was %s class", expected, actual),
                expected, actual);
    }

    @Test
    public void getOperationHandler_purchaseOperation_ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<?> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(String.format("Should return %s class but was %s class", expected, actual),
                expected, actual);
    }

    @Test
    public void getOperationHandler_returnseOperation_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<?> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(String.format("Should return %s class but was %s class", expected, actual),
                expected, actual);
    }

    @Test
    public void getOperationHandler_supplyOperation_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<?> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(String.format("Should return %s class but was %s class", expected, actual),
                expected, actual);
    }
}