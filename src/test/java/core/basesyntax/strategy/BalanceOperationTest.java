package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final OperationStrategy balanceOperation = new BalanceOperation();

    @Test
    public void process_qtyChanges_ok() {
        int expected = 7;
        int actual = this.balanceOperation.process(2, 7);

        assertEquals(expected, actual);
    }
}
