package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.SupplyHandler;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    @Test
    void handle_incrementExisting_ok() {
        Storage storage = new Storage();
        storage.add("apple", 10);
        new SupplyHandler(storage).handle("apple", 5);
        assertEquals(15, (int) storage.get("apple"));
    }
}
