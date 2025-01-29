package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balance;

    @BeforeAll
    static void beforeAll() {
        balance = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void balance_ok() {
        balance.handle(Storage.storage,
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 19));
        assertNotNull(Storage.storage);
        assertEquals(19,Storage.storage.get("banana"));
    }
}
