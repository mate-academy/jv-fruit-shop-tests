package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitTransaction.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getStrategy_balance_ok() {
        Class<? extends OperationHandler> expectedClass = BalanceOperationHandler.class;
        OperationHandler actual = operationStrategy.getStrategy(Operation.BALANCE);
        assertEquals(expectedClass, actual.getClass());
    }

    @Test
    public void getStrategy_purchase_ok() {
        Class<? extends OperationHandler> expectedClass = PurchaseOperationHandler.class;
        OperationHandler actual = operationStrategy.getStrategy(Operation.PURCHASE);
        assertEquals(expectedClass, actual.getClass());
    }

    @Test
    public void getStrategy_return_ok() {
        Class<? extends OperationHandler> expectedClass = ReturnOperationHandler.class;
        OperationHandler actual = operationStrategy.getStrategy(Operation.RETURN);
        assertEquals(expectedClass, actual.getClass());
    }

    @Test
    public void getStrategy_supply_ok() {
        Class<? extends OperationHandler> expectedClass = SupplyOperationHandler.class;
        OperationHandler actual = operationStrategy.getStrategy(Operation.SUPPLY);
        assertEquals(expectedClass, actual.getClass());
    }
}
