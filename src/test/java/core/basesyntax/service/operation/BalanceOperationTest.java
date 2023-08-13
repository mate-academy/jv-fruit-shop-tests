package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balance;

    @BeforeAll
    static void setup() {
        balance = new BalanceOperation();
    }

    @Test
    void processWithTransaction_emptyInputParameters_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            balance.processWithTransaction(null);
        });
    }
}
