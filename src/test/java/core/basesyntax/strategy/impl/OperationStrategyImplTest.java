package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void validReturnClass_Ok() {
        Class<? extends OperationHandler> actualBalance = operationStrategy
                .getOperation(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(BalanceOperationHandler.class, actualBalance);
        Class<? extends OperationHandler> actualReturn = operationStrategy
                .getOperation(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(ReturnOperationHandler.class, actualReturn);
        Class<? extends OperationHandler> actualSupply = operationStrategy
                .getOperation(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(SupplyOperationHandler.class, actualSupply);
        Class<? extends OperationHandler> actualPurchase = operationStrategy
                .getOperation(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(PurchaseOperationHandler.class, actualPurchase);
    }
}
