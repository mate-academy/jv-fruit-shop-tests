package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getStrategy_balance_ok() {
        OperationHandler actual = operationStrategy.getStrategy(Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void getStrategy_purchase_ok() {
        OperationHandler actual = operationStrategy.getStrategy(Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void getStrategy_return_ok() {
        OperationHandler actual = operationStrategy.getStrategy(Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test
    public void getStrategy_supply_ok() {
        OperationHandler actual = operationStrategy.getStrategy(Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, actual.getClass());
    }
}
