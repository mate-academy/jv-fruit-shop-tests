package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setup() {
        operationHandler = new BalanceOperation();
    }

    @Test
    void testProcessWithTransaction_successful() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10);
        assertDoesNotThrow(() -> operationHandler.processWithTransaction(transaction));
    }

    @Test
    void testProcessWithTransaction_nullTransaction_throwsException() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.processWithTransaction(null));
    }
}
