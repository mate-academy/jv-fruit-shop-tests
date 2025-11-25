package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final OperationStrategy returnOperation = new ReturnOperation();

    @Test
    public void process_qtyChanges_ok() {
        int expected = 9;
        int actual = this.returnOperation.process(2, 7);

        assertEquals(expected, actual);
    }
}
