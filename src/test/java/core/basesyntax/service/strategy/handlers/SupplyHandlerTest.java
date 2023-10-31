package core.basesyntax.service.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {

    private static SupplyHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
        Storage.fruitsMap.clear();
        Storage.fruitsMap.put("cherry", 1);
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction("s", "cherry", 15);
        supplyHandler.handleTransaction(transaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("cherry", 16);
        assertEquals(expected.entrySet(), Storage.fruitsMap.entrySet());
    }
}
