package core.basesyntax.db.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.strategy.OperationHandler;
import core.basesyntax.db.strategy.OperationStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void get_supplyOperation_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_balanceOperation_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_returnOperation_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void get_purchaseOperation_ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void getOperationHandler_nullOperation_notOk() {
        operationStrategy.get(null);
    }
}
