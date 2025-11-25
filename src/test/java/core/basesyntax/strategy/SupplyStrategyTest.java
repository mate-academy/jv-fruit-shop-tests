package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SupplyStrategyTest {
    private final OperationStrategy supplyOperation = new SupplyStrategy();

    @Test
    public void process_qtyChanges_ok() {
        int expected = 9;
        int actual = this.supplyOperation.process(2, 7);

        assertEquals(expected, actual);
    }
}
