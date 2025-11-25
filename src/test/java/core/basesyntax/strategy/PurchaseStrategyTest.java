package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PurchaseStrategyTest {
    private final OperationStrategy purchaseOperation = new PurchaseStrategy();

    @Test
    public void process_qtyChanges_ok() {
        int expected = 6;
        int actual = this.purchaseOperation.process(11, 5);

        assertEquals(expected, actual);
    }
}
