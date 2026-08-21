package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final BalanceOperation balanceOperation = new BalanceOperation();

    @Test
    void apply_zeroQuantity_ok() {
        int actual = balanceOperation.apply(0, 100);
        assertEquals(100, actual);
    }

    @Test
    void apply_notZeroQuantity_ok() {
        int actual = balanceOperation.apply(20, 50);
        assertEquals(50, actual);
    }

    @Test
    void apply_zero_ok() {
        int actual = balanceOperation.apply(0, 0);
        assertEquals(0, actual);
    }
}
