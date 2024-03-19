package core.basesyntax.service.impl.strategy;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceStrategyImplTest {

    @Test
    void shouldReturnSameValueWhenCalled_calculate_ok() {
        OperationHandler handler = new BalanceStrategyImpl();
        int amount = 100;
        int expected = 100;
        int actual = handler.calculate(amount);
        Assertions.assertEquals(expected, actual);
    }
}
