package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.STORAGE.clear();
    }

    @Test
    void processTransaction_valid_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 100);
        assertDoesNotThrow(() -> balanceOperationHandler.process(transaction));
        int expected = FruitStorage.STORAGE.get("banana");
        assertEquals(expected, 100);
    }
}
