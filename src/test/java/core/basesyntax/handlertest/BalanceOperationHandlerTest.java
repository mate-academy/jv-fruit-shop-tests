package core.basesyntax.handlertest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.DATABASE.clear();
    }

    @Test
    void handleTransaction_valid_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 100);
        assertDoesNotThrow(() -> balanceOperationHandler.handleTransaction(transaction));
        int actual = Storage.DATABASE.get("banana");
        assertEquals(100, actual);
    }
}
