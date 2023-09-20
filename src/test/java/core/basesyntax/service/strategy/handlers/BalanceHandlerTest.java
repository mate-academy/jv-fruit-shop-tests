package core.basesyntax.service.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
        Storage.fruitsMap.clear();
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction("b", "cherry", 100);
        balanceHandler.handleTransaction(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("cherry", 100);
        assertEquals(expected.entrySet(), Storage.fruitsMap.entrySet());
    }

    @Test
    void handleTransaction_existentFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction("b", "cherry", 1);
        assertThrows(RuntimeException.class,
                () -> balanceHandler.handleTransaction(transaction));
    }
}
