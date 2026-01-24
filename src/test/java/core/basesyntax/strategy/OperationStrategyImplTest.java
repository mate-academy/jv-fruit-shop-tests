package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private static OperationStrategy strategy;

    @BeforeAll
    static void init() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    void get_ShouldReturnHandler_WhenOperationExists() {
        assertNotNull(strategy.get(Storage.Operation.BALANCE));
    }

    @Test
    void get_ShouldThrowException_WhenOperationIsNull() {
        assertThrows(RuntimeException.class,
                () -> strategy.get(null));
    }
}
