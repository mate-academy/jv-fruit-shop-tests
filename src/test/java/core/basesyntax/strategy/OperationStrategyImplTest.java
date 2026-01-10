package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationStrategyImplTest {

    @Test
    void shouldReturnHandler() {
        OperationStrategy strategy = new OperationStrategyImpl();

        assertNotNull(strategy.get(Storage.Operation.BALANCE));
    }

    @Test
    void shouldThrowForNullOperation() {
        OperationStrategy strategy = new OperationStrategyImpl();

        assertThrows(RuntimeException.class,
                () -> strategy.get(null));
    }
}
