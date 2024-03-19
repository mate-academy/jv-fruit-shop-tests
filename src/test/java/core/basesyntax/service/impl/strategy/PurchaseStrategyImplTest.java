package core.basesyntax.service.impl.strategy;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseStrategyImplTest {

    @Test
    void shouldReturnNegativeValueWhenCalled_calculate_ok() {
        OperationHandler handler = new PurchaseStrategyImpl();
        int amount = 100;
        int expected = -100;
        int actual = handler.calculate(amount);
        Assertions.assertEquals(expected, actual);
    }
}
