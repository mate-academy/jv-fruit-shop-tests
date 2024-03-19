package core.basesyntax.service.impl.strategy;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.SupplyStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyStrategyImplTest {

    @Test
    void shouldReturnSameValueWhenCalled_calculate_ok() {
        OperationHandler handler = new SupplyStrategyImpl();
        int amount = 100;
        int expected = 100;
        int actual = handler.calculate(amount);
        Assertions.assertEquals(expected, actual);
    }
}
