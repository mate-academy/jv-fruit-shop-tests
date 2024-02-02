package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private final OperationStrategy strategy = new OperationStrategy();

    @Test
    void testWriteToStorageWithNullContent() {
        assertThrows(RuntimeException.class, () -> strategy.getOperationHandler(null));
    }
}
