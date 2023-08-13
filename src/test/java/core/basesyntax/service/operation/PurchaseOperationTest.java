package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler purchase;

    @BeforeAll
    static void setup() {
        purchase = new PurchaseOperation();
    }

    @Test
    void processWithTransaction_emptyInputParameters_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            purchase.processWithTransaction(null);
        });
    }
}
