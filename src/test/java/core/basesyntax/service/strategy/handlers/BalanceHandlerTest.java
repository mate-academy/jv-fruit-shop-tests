package core.basesyntax.service.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
        Storage.fruitsMap.clear();
    }

    @AfterEach
    void tearDown() {
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
}
