package core.basesyntax.model.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void balanceNullFruitTransactionNotOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.handle(null));
    }

    @Test
    void balanceOk() {
        Storage.getStorage().put("banana", 100);
        balanceOperation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 33));
        Map.Entry<String, Integer> expectedEntry = Map.entry("banana", 33);
        assertTrue(Storage.getStorage().entrySet().contains(expectedEntry));
    }
}
