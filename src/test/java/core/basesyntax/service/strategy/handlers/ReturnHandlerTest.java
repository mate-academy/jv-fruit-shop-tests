package core.basesyntax.service.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static ReturnHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
        Storage.fruitsMap.clear();
        Storage.fruitsMap.put("cherry", 3);
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction("r", "cherry", 18);
        returnHandler.handleTransaction(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("cherry", 21);
        assertEquals(expected.entrySet(), Storage.fruitsMap.entrySet());
    }

    @Test
    void handleTransaction_nonexistentFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction("r", "banana", 9);
        assertThrows(RuntimeException.class,
                () -> returnHandler.handleTransaction(transaction));
    }

}
