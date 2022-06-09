package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.strategy.QuantityHandler;
import core.basesyntax.strategy.QuantityStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuantityStrategyImplTest {
    private static final QuantityStrategy quantityStrategy = new QuantityStrategyImpl();

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getQuantityHandler_validOperation_Ok() {
        QuantityHandler actualResult1 = quantityStrategy.getQuantityHandler(Operation.BALANCE);
        assertEquals(PositiveQuantityHandlerImpl.class, actualResult1.getClass());
        QuantityHandler actualResult2 = quantityStrategy.getQuantityHandler(Operation.RETURN);
        assertEquals(PositiveQuantityHandlerImpl.class, actualResult2.getClass());
        QuantityHandler actualResult3 = quantityStrategy.getQuantityHandler(Operation.SUPPLY);
        assertEquals(PositiveQuantityHandlerImpl.class, actualResult3.getClass());
        QuantityHandler actualResult4 = quantityStrategy.getQuantityHandler(Operation.PURCHASE);
        assertEquals(NegativeQuantityHandlerImpl.class, actualResult4.getClass());
    }

    @Test
    public void getQuantityHandler_nullOperation_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit operation cannot be null");
        quantityStrategy.getQuantityHandler(null);
    }
}
