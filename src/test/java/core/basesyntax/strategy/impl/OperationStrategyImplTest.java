package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationStrategyImplTest {
    private static final OperationStrategy operationStrategy = new OperationStrategyImpl();

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getOperationHandler_validOperation_Ok() {
        OperationHandler actualResult1 = operationStrategy.getOperationHandler(Operation.BALANCE);
        assertEquals(PositiveOperationHandlerImpl.class, actualResult1.getClass());
        OperationHandler actualResult2 = operationStrategy.getOperationHandler(Operation.RETURN);
        assertEquals(PositiveOperationHandlerImpl.class, actualResult2.getClass());
        OperationHandler actualResult3 = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertEquals(PositiveOperationHandlerImpl.class, actualResult3.getClass());
        OperationHandler actualResult4 = operationStrategy.getOperationHandler(Operation.PURCHASE);
        assertEquals(NegativeOperationHandlerImpl.class, actualResult4.getClass());
    }

    @Test
    public void getOperationHandler_nullOperation_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit operation cannot be null");
        operationStrategy.getOperationHandler(null);
    }
}
