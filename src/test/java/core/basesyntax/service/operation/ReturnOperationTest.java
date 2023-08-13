package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationHandler returnOperation;

    @BeforeAll
    static void setup() {
        returnOperation = new ReturnOperation();
    }

    @Test
    void processWithTransaction_emptyInputParameters_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            returnOperation.processWithTransaction(null);
        });
    }
}
