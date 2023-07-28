package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {

    @BeforeAll
    static void beforeAll() {
        Storage.storage.clear();
        Storage.storage.put("banana", 200);
    }

    @Test
    void handler_SupplyCorrectWork_Ok() {
        SupplyHandler supplyHandler = new SupplyHandler();
        supplyHandler.execute("banana", 50);
        assertEquals(Storage.storage.get("banana"), 250);
    }
}
