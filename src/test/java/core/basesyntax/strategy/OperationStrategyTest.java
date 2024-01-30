package core.basesyntax.strategy;

import core.basesyntax.transaction.TransactionProcessorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;

class OperationStrategyTest {
    OperationStrategy strategy = new OperationStrategy();

    @Test
    void testWriteToStorageWithNullContent() {
        assertThrows(RuntimeException.class, () -> strategy.getOperationHandler(null));
    }
}