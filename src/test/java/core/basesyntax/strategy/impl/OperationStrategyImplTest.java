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
        OperationHandler actualResultFirst = operationStrategy.getOperationHandler(Operation.BALANCE);
        assertEquals(PositiveOperationHandlerImpl.class, actualResultFirst.getClass());
        OperationHandler actualResultSecond = operationStrategy.getOperationHandler(Operation.RETURN);
        assertEquals(PositiveOperationHandlerImpl.class, actualResultSecond.getClass());
        OperationHandler actualResultThird = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertEquals(PositiveOperationHandlerImpl.class, actualResultThird.getClass());
        OperationHandler actualResultFourth = operationStrategy.getOperationHandler(Operation.PURCHASE);
        assertEquals(NegativeOperationHandlerImpl.class, actualResultFourth.getClass());
    }

    @Test
    public void getOperationHandler_nullOperation_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit operation cannot be null");
        operationStrategy.getOperationHandler(null);
    }
}
