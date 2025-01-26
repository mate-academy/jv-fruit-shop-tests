package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balance;

    @BeforeAll
    static void beforeAll() {
        balance = new BalanceOperation();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void balanceTest() {
        balance.handle(Storage.storage,
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana", 19));
        assertNotNull(Storage.storage);
        assertEquals(19,Storage.storage.get("banana"));
    }
}
