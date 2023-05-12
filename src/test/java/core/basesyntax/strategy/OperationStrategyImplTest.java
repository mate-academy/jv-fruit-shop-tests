package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void get_ValidData_ok() {
        OperationHandler operationHandlerActual = operationStrategy
                .get(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperationStrategy.class, operationHandlerActual.getClass());
        operationHandlerActual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperationStrategy.class, operationHandlerActual.getClass());
        operationHandlerActual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperationStrategy.class, operationHandlerActual.getClass());
        operationHandlerActual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyOperationStrategy.class, operationHandlerActual.getClass());
    }
}
