package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler supply;

    @BeforeAll
    static void setup() {
        supply = new SupplyOperation();
    }

    @Test
    void processWithTransaction_emptyInputParameters_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            supply.processWithTransaction(null);
        });
    }
}
