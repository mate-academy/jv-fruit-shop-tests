package core.basesyntax.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    void apply_goodOutput_ok() {
        Assertions.assertEquals(4, balanceHandler.apply(2, 4));
    }

    @Test
    void apply_ignoreCurrentValue_ok() {
        Assertions.assertEquals(4, balanceHandler.apply(100, 4));
    }

    @Test
    void apply_transactionIsZero_ok() {
        Assertions.assertEquals(0, balanceHandler.apply(2, 0));
    }
}
